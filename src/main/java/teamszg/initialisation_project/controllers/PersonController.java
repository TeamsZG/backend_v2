package teamszg.initialisation_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.services.PersonService;

@RestController
@CrossOrigin
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/search")
    public Person searchByName(@RequestParam String name) throws Exception {
        return personService.searchByName(name);
    }

    @PostMapping("/ajout")
    public Person ajouterPersonne(@RequestBody  Person person) throws Exception {
        return personService.addClient(person);
    }

    @PutMapping("/update")
    public Person updatePerson(@RequestParam String name, @RequestBody Person person) throws Exception {
        return personService.updatePerson(name, person);
    }

    @DeleteMapping("/delete")
    public String deletePerson(@RequestParam String name) throws Exception {
        personService.deletePerson(name);
        return name + " has been deleted successfully";
    }





}
