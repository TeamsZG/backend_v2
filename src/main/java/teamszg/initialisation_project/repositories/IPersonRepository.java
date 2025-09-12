package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Person;

public interface IPersonRepository extends JpaRepository<Person, Long> {

}
