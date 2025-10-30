package teamszg.initialisation_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.PersonService;

import java.util.List;
import java.util.Set;

/**
 * Contrôleur REST responsable de la gestion des opérations CRUD sur les entités {@link Person}.
 * <p>
 * Ce contrôleur permet de récupérer, rechercher, ajouter, modifier et supprimer des personnes.
 * Il offre également des endpoints pour consulter et modifier l’historique de visionnage
 * associé à une personne.
 * </p>
 */

@RestController
@CrossOrigin
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() throws Exception {
        return personService.getAllPersons();
    }

    @GetMapping("/search")
    public List<Person> searchByName(@RequestParam String name) throws Exception {
        return personService.searchByName(name);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) throws Exception {
        return personService.addPersons(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person person) throws Exception {
        return personService.updatePerson(id, person);

    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable Long id) throws Exception {
        personService.deletePerson(id);
        return "Supression de l'id personne: " + id ;
    }

    @GetMapping("/{id}/history")
    public Set<Series> getHistorique(@PathVariable Long id) throws Exception {
        return personService.getAllHistory(id);
    }

    @PostMapping("/{id}/history/{seriesId}")
    public Person addHistory(@PathVariable Long id, @PathVariable Long seriesId) throws Exception {
        return personService.addHistory(id, seriesId);
    }

}
