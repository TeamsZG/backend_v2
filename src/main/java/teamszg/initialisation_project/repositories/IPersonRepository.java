package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Person;

import java.util.List;


public interface IPersonRepository extends JpaRepository<Person, Long> {
    Person findPersonById(Long id);
    List<Person> findAllByName(String name);



}
