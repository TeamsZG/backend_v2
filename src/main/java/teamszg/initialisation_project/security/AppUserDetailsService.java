package teamszg.initialisation_project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;
import java.util.Collections;

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

        return org.springframework.security.core.userdetails.User
                .withUsername(p.getEmail())
                .password(p.getPasswordHash())
                .authorities(Collections.emptyList())
                .build();
    }
}

