// src/test/java/services/RatingSerieServiceExtraTest.java
package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.IRatingSerieRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;
import teamszg.initialisation_project.services.RatingSerieService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RatingSerieServiceExtraTest {

    private IRatingSerieRepository ratingRepo;
    private ISeriesRepository seriesRepo;
    private IPersonRepository personRepo;
    private RatingSerieService service;

    @BeforeEach
    void setup() {
        ratingRepo = Mockito.mock(IRatingSerieRepository.class);
        seriesRepo = Mockito.mock(ISeriesRepository.class);
        personRepo = Mockito.mock(IPersonRepository.class);
        service = new RatingSerieService(ratingRepo, seriesRepo, personRepo);
    }

    @Test
    void addRatingSerie_updates_avg_ok() {
        Person p = new Person(); p.setId(1L);
        Series s = new Series(); s.setId(2L);
        when(personRepo.findPersonById(1L)).thenReturn(p);
        when(seriesRepo.findSeriesById(2L)).thenReturn(s);
        when(ratingRepo.findByPersonAndSeries(p, s)).thenReturn(null);
        when(ratingRepo.moyenneParSerie(2L)).thenReturn(8.9);

        RatingSerie out = service.addRatingSerie(1L, 2L, 9.5);
        assertNotNull(out);
        assertEquals(8.9, s.getNote());
    }

    @Test
    void addRatingSerie_invalidRating_throws() {
        Person p = new Person(); p.setId(1L);
        Series s = new Series(); s.setId(2L);
        when(personRepo.findPersonById(1L)).thenReturn(p);
        when(seriesRepo.findSeriesById(2L)).thenReturn(s);

        assertThrows(RuntimeException.class, () -> service.addRatingSerie(1L, 2L, 11));
    }

    @Test
    void getRatingSerie_ok() {
        when(ratingRepo.moyenneParSerie(5L)).thenReturn(7.7);
        assertEquals(7.7, service.getRatingSerie(5L));
    }
}
