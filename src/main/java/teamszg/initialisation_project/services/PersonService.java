package teamszg.initialisation_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;

import java.util.List;
@Service
public class PersonService {

    @Autowired
    private final IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<Person> search(String personName){
        return personRepository.findAll();
    }
}
