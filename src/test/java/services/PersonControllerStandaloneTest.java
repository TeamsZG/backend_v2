// src/test/java/services/PersonControllerStandaloneTest.java
package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamszg.initialisation_project.controllers.PersonController;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.PersonService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PersonControllerStandaloneTest {

    private MockMvc mvc;
    private PersonService personService;
    private final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setup() throws Exception {
        personService = Mockito.mock(PersonService.class);
        PersonController controller = new PersonController();

        // inject @Autowired field via reflection
        Field f = PersonController.class.getDeclaredField("personService");
        f.setAccessible(true);
        f.set(controller, personService);

        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllPersons_ok() throws Exception {
        Person a = new Person(); a.setId(1L); a.setName("Alice");
        Person b = new Person(); b.setId(2L); b.setName("Bob");
        when(personService.getAllPersons()).thenReturn(List.of(a,b));

        mvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    void searchByName_ok() throws Exception {
        Person p = new Person(); p.setId(10L); p.setName("Mario");
        when(personService.searchByName("mario")).thenReturn(List.of(p));

        mvc.perform(get("/persons/search").param("name","mario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void createPerson_ok() throws Exception {
        Person in = new Person(); in.setName("Neo");
        Person out = new Person(); out.setId(7L); out.setName("Neo");
        when(personService.addPersons(any(Person.class))).thenReturn(out);

        mvc.perform(post("/persons").contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Neo"));
    }

    @Test
    void updatePerson_ok() throws Exception {
        Person in = new Person(); in.setName("Updated"); in.setAge(30);
        Person out = new Person(); out.setId(3L); out.setName("Updated"); out.setAge(30);
        when(personService.updatePerson(eq(3L), any(Person.class))).thenReturn(out);

        mvc.perform(put("/persons/3").contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void deletePerson_ok() throws Exception {
        mvc.perform(delete("/persons/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Supression de l'id personne: 5"));
    }

    @Test
    void history_ok() throws Exception {
        Series s = new Series(); s.setId(9L); s.setTitle("Breaking Future");
        when(personService.getAllHistory(1L)).thenReturn(Set.of(s));

        mvc.perform(get("/persons/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(9));
    }

    @Test
    void addHistory_ok() throws Exception {
        Person p = new Person(); p.setId(1L);
        when(personService.addHistory(1L, 2L)).thenReturn(p);

        mvc.perform(post("/persons/1/history/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
