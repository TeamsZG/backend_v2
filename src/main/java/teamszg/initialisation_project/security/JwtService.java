package teamszg.initialisation_project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service pour gérer la création et la validation des tokens JWT.
 * <p>
 * Fournit des méthodes pour générer un token signé pour un utilisateur et
 * pour extraire les claims (données) depuis un token existant.
 * </p>
 */

@Service
public class JwtService {
    // clé HMAC (en mémoire pour ton TP). En prod: clé externe/variable d'env.
    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long expirationMs = 24 * 60 * 60 * 1000; // 24h

    public String generateToken(Long userId, String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .subject(email)
                .claim("uid", userId)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
    }
}
