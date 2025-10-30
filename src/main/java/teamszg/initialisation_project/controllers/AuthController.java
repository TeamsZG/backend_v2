package teamszg.initialisation_project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.security.JwtService;

import java.util.Map;

/**
 * Contrôleur responsable de la gestion de l'authentification des utilisateurs.
 * <p>
 * Cette classe gère la connexion d'un utilisateur à partir de son adresse e-mail et de son mot de passe.
 * Si les informations d'identification sont valides, un token JWT est généré et renvoyé.
 * </p>
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IPersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(IPersonRepository personRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest body) {
        Person p = personRepository.findByEmail(body.email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(body.password, p.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtService.generateToken(p.getId(), p.getEmail());
        return Map.of("token", token, "tokenType", "Bearer", "expiresIn", 24*60*60);
    }
}
