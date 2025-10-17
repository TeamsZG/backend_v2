package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamszg.initialisation_project.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
    Person findPersonById(Long id);
    List<Person> findAllByNameIgnoreCase(String name);
    Optional<Person> findByEmail(String email);
}
