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


    public Person searchByName(String name) {
        return personRepository.findPersonByName(name);
    }

    public Person addClient(Person person){
        return personRepository.save(person);
    }

    public Person updatePerson(String name, Person updatePerson) throws Exception {
        Person personAvantUpdate = personRepository.findPersonByName(name);
        personAvantUpdate.setName(updatePerson.getName());
        personAvantUpdate.setAge(updatePerson.getAge());
        personAvantUpdate.setGender(updatePerson.getGender());

        return personRepository.save(personAvantUpdate);
    }

    public void deletePerson(String name) throws Exception {
        Person person = personRepository.findPersonByName(name);
        personRepository.delete(person);
    }

}