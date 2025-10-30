package teamszg.initialisation_project.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre de sécurité JWT pour Spring Security.
 * <p>
 * Ce filtre intercepte chaque requête HTTP et vérifie si un token JWT est présent dans l'en-tête
 * "Authorization". Si un token valide est trouvé, il configure l'utilisateur authentifié dans le
 * contexte de sécurité Spring.
 * </p>
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService uds;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService uds) {
        this.jwtService = jwtService;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }
        String token = auth.substring(7);
        try {
            Claims c = jwtService.parseClaims(token);
            String email = c.getSubject();
            Long uid = c.get("uid", Long.class);

            var user = uds.loadUserByUsername(email);
            var authToken = new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(uid, email), null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            chain.doFilter(req, res);
        } catch (Exception e) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

