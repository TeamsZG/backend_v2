package teamszg.initialisation_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;

@Configuration
public class Fill {

    @Bean
    public CommandLineRunner backfillPeople(IPersonRepository repo, PasswordEncoder pe) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                var people = repo.findAll();
                for (Person p : people) {
                    boolean changed = false;

                    if (p.getEmail() == null || p.getEmail().isBlank()) {
                        String base = (p.getName() == null)
                                ? "user"
                                : p.getName().trim().toLowerCase().replaceAll("\\s+", ".");
                        p.setEmail(base + "." + p.getId() + "@example.com");
                        changed = true;
                    }
                    if (p.getPasswordHash() == null || p.getPasswordHash().isBlank()) {
                        p.setPasswordHash(pe.encode("password123"));
                        changed = true;
                    }
                    if (changed) {
                        repo.save(p);
                    }
                }

                // Optionnel : affiche les utilisateurs mis à jour
                repo.findAll().forEach(p ->
                        System.out.println("User: " + p.getName() + " -> " + p.getEmail())
                );
            }
        };
    }
}
