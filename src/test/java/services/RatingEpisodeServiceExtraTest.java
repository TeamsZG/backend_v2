// src/test/java/services/RatingEpisodeServiceExtraTest.java
package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teamszg.initialisation_project.models.Episode;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IEpisodeRepository;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.IRatingEpisodeRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;
import teamszg.initialisation_project.services.RatingEpisodeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RatingEpisodeServiceExtraTest {

    private IRatingEpisodeRepository ratingRepo;
    private ISeriesRepository seriesRepo;
    private IPersonRepository personRepo;
    private IEpisodeRepository episodeRepo;
    private RatingEpisodeService service;

    @BeforeEach
    void setup() {
        ratingRepo = Mockito.mock(IRatingEpisodeRepository.class);
        seriesRepo = Mockito.mock(ISeriesRepository.class);
        personRepo = Mockito.mock(IPersonRepository.class);
        episodeRepo = Mockito.mock(IEpisodeRepository.class);
        service = new RatingEpisodeService(ratingRepo, seriesRepo, personRepo, episodeRepo);
    }

    private Episode epWithSeries(long epId, long seriesId) {
        Series s = new Series(); s.setId(seriesId);
        Episode e = new Episode(); e.setId(epId); e.setSeries(s);
        return e;
    }

    @Test
    void addRatingEpisode_creates_and_updates_avg_ok() {
        Person p = new Person(); p.setId(1L);
        Episode e = epWithSeries(10L, 2L);
        Series s = new Series(); s.setId(2L);

        when(personRepo.findPersonById(1L)).thenReturn(p);
        when(episodeRepo.findEpisodeById(10L)).thenReturn(e);
        when(seriesRepo.findSeriesById(2L)).thenReturn(s);
        when(ratingRepo.findRatingEpisodeByEpisodeAndSeriesAndPerson(e, s, p)).thenReturn(null);
        when(ratingRepo.moyenneParEpisode(10L)).thenReturn(8.0);

        RatingEpisode out = service.addRatingEpisode(1L, 10L, 8.0, 2L);
        assertNotNull(out);
        assertEquals(8.0, e.getRating());
    }

    @Test
    void getRatingEpisode_ok() {
        Episode e = epWithSeries(10L, 3L);
        Series s = new Series(); s.setId(3L);
        when(episodeRepo.findEpisodeById(10L)).thenReturn(e);
        when(seriesRepo.findSeriesById(3L)).thenReturn(s);
        when(ratingRepo.moyenneParEpisode(10L)).thenReturn(7.5);

        double avg = service.getRatingEpisode(10L, 3L);
        assertEquals(7.5, avg);
    }

    @Test
    void addRatingEpisode_wrongSeries_throws() {
        Person p = new Person(); p.setId(1L);
        Episode e = epWithSeries(10L, 99L);
        Series s = new Series(); s.setId(2L);

        when(personRepo.findPersonById(1L)).thenReturn(p);
        when(episodeRepo.findEpisodeById(10L)).thenReturn(e);
        when(seriesRepo.findSeriesById(2L)).thenReturn(s);

        assertThrows(RuntimeException.class, () -> service.addRatingEpisode(1L, 10L, 8.0, 2L));
    }
}
