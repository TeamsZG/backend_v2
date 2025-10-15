package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.List;
import java.util.Set;

@Service
public class PersonService {

    private final IPersonRepository personRepository;
    private final ISeriesRepository seriesRepository;

    public PersonService(IPersonRepository personRepository, ISeriesRepository seriesRepository) {
        this.personRepository = personRepository;
        this.seriesRepository = seriesRepository;
    }

    public List<Person> searchByName(String name) throws Exception {
        return personRepository.findAllByNameIgnoreCase(name);
    }

    public Person addPersons(Person person) throws Exception {
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() throws Exception {
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()) {
            System.out.println("Pas de personne dans la BD");
        }
        return persons;
    }

    public Set<Series> getAllHistory(Long personId) throws Exception {
        Person person = personRepository.findPersonById(personId);
        if (person == null) {
            throw new Exception("La personne avec l'ID " + personId + " n'a pas été trouvée.");
        }
        return person.getHistory();
    }

    public Person getObjectPersonById(Long id){
        Person person = personRepository.findPersonById(id);
                return person;
    }

    public Person addHistory(Long personId, Long seriesId) throws Exception {
        Person person = personRepository.findPersonById(personId);
        Series series = seriesRepository.findSeriesById(seriesId);
        if (!person.getHistory().contains(series)) {
            person.getHistory().add(series);
        } else {
            // assez inutile... possiblement on va le supprimer
            System.out.println("Cette série : "+ seriesId+ " est déjà dans l'historique de " + personId);
        }
        return personRepository.save(person);
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


