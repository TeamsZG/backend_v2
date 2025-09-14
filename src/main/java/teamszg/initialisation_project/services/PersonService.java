package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.repositories.IPersonRepository;

import java.util.List;

@Service
public class PersonService {

    private final IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> searchByName(String name) throws Exception {
        return personRepository.findAllByName(name);
    }

    public Person addClient(Person person) throws Exception {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() throws Exception {
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()) {
            System.out.println("Pas de personne dans la BD");
        }
        return persons;
    }

    public Person updatePerson(Long id, Person updatePerson) throws Exception {
        Person personAvantUpdate = personRepository.findPersonById(id);
        if (personAvantUpdate == null) {
            throw new Exception("La personne avec l'ID " + id + " n'a pas été trouvée.");
        }

        personAvantUpdate.setName(updatePerson.getName());
        personAvantUpdate.setGender(updatePerson.getGender());
        personAvantUpdate.setAge(updatePerson.getAge());

        return personRepository.save(personAvantUpdate);
    }

    public void deletePerson(Long id) throws Exception {
        Person personSupression = personRepository.findPersonById(id);
        if (personSupression == null) {
            throw new Exception("La personne avec l'ID " + id + " n'a pas été trouvée.");
        }

        personRepository.delete(personSupression);
    }
}


