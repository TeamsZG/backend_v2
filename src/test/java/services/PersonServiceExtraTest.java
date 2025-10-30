// src/test/java/services/PersonServiceExtraTest.java
package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;
import teamszg.initialisation_project.services.PersonService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PersonServiceExtraTest {

    private IPersonRepository personRepo;
    private ISeriesRepository seriesRepo;
    private PersonService service;

    @BeforeEach
    void setup() {
        personRepo = Mockito.mock(IPersonRepository.class);
        seriesRepo = Mockito.mock(ISeriesRepository.class);
        service = new PersonService(personRepo, seriesRepo);
    }

    @Test
    void getAllPersons_empty_ok() throws Exception {
        when(personRepo.findAll()).thenReturn(List.of());
        assertTrue(service.getAllPersons().isEmpty());
    }

    @Test
    void getAllHistory_ok() throws Exception {
        Series s = new Series(); s.setId(1L);
        Person p = new Person(); p.setId(9L); p.setHistory(Set.of(s));
        when(personRepo.findPersonById(9L)).thenReturn(p);

        var hist = service.getAllHistory(9L);
        assertEquals(1, hist.size());
        assertTrue(hist.iterator().next().getId().equals(1L));
    }

    @Test
    void getAllHistory_notFound_throws() {
        when(personRepo.findPersonById(99L)).thenReturn(null);
        assertThrows(Exception.class, () -> service.getAllHistory(99L));
    }

    @Test
    void addHistory_adds_if_absent() throws Exception {
        Person p = new Person(); p.setId(1L);
        p.setHistory(new java.util.HashSet<>());
        Series s = new Series(); s.setId(5L);
        when(personRepo.findPersonById(1L)).thenReturn(p);
        when(seriesRepo.findSeriesById(5L)).thenReturn(s);
        when(personRepo.save(any(Person.class))).thenAnswer(i -> i.getArgument(0));

        Person saved = service.addHistory(1L, 5L);
        assertEquals(1, saved.getHistory().size());
    }

    @Test
    void updatePerson_notFound_throws() {
        when(personRepo.findPersonById(7L)).thenReturn(null);
        Person in = new Person(); in.setName("x");
        assertThrows(Exception.class, () -> service.updatePerson(7L, in));
    }

    @Test
    void deletePerson_notFound_throws() {
        when(personRepo.findPersonById(7L)).thenReturn(null);
        assertThrows(Exception.class, () -> service.deletePerson(7L));
    }
}
