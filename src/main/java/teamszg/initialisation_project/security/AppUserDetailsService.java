package teamszg.initialisation_project.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;

import java.util.Collections;

/**
 * Service de gestion des détails d'un utilisateur pour Spring Security.
 * <p>
 * Implémente {@link UserDetailsService} afin de charger les informations d'authentification
 * d'un utilisateur à partir de son email.
 * </p>
 */

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final IPersonRepository repo;

    public AppUserDetailsService(IPersonRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person p = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return User
                .withUsername(p.getEmail())
                .password(p.getPasswordHash())
                .authorities(Collections.emptyList())
                .build();
    }
}

