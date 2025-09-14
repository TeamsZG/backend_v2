package teamszg.initialisation_project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.services.PersonService;

import java.util.List;

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
}
