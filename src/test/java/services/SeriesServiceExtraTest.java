// src/test/java/services/SeriesServiceExtraTest.java
package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.ISeriesRepository;
import teamszg.initialisation_project.services.SeriesService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SeriesServiceExtraTest {

    private ISeriesRepository repo;
    private SeriesService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(ISeriesRepository.class);
        service = new SeriesService(repo);
    }

    @Test
    void findAllSeries_empty_ok() throws Exception {
        when(repo.findAll()).thenReturn(List.of());
        assertEquals(0, service.findAllSeries().size());
    }

    @Test
    void findSeriesById_notFound_throws() {
        when(repo.findSeriesById(999L)).thenReturn(null);
        Exception ex = assertThrows(Exception.class, () -> service.findSeriesById(999L));
        assertTrue(ex.getMessage().contains("n'a pas été trouvée"));
    }

    @Test
    void updateSeries_notFound_throws() {
        when(repo.findSeriesById(5L)).thenReturn(null);
        Series s = new Series(); s.setTitle("x");
        assertThrows(Exception.class, () -> service.updateSeries(5L, s));
    }

    @Test
    void deleteSeries_notFound_throws() {
        when(repo.findSeriesById(5L)).thenReturn(null);
        assertThrows(Exception.class, () -> service.deleteSeries(5L));
    }

    @Test
    void search_genre_only_ok() throws Exception {
        Series a = new Series(); a.setId(1L); a.setGenre("SciFi");
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(a)));
        when(repo.findSeriesByGenreIgnoreCase("SciFi")).thenReturn(List.of(a));

        var out = service.search("SciFi", null, null);
        assertEquals(1, out.size());
        assertEquals(1L, out.get(0).getId());
    }

    @Test
    void search_title_only_ok() throws Exception {
        Series a = new Series(); a.setId(2L); a.setTitle("Friends");
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(a)));
        when(repo.findSeriesBytitleIgnoreCase("Friends")).thenReturn(List.of(a));

        var out = service.search(null, "Friends", null);
        assertEquals(1, out.size());
        assertEquals(2L, out.get(0).getId());
    }

    @Test
    void search_minEpisodes_only_ok() throws Exception {
        Series a = new Series(); a.setId(3L); a.setNbEpisodes(100);
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(a)));
        when(repo.findSeriesByNbEpisodesGreaterThanEqual(50)).thenReturn(List.of(a));

        var out = service.search(null, null, 50);
        assertEquals(1, out.size());
        assertEquals(3L, out.get(0).getId());
    }

    @Test
    void search_invalid_filter_throws() {
        Exception ex = assertThrows(Exception.class, () -> service.search(null, "", 0));
        assertTrue(ex.getMessage().contains("Oups filtre non validé"));
    }

    @Test
    void trending_ok_sorted_and_limited() throws Exception {
        Series s1 = new Series(); s1.setId(1L); s1.setViews(100); s1.setNote(8.0);
        Series s2 = new Series(); s2.setId(2L); s2.setViews(50); s2.setNote(9.5);
        when(repo.findAll()).thenReturn(new ArrayList<>(List.of(s1, s2)));

        var out = service.trending();
        assertEquals(2, out.size());
        // s1 score = 70 + 2.4 = 72.4 ; s2 = 35 + 2.85 = 37.85
        assertEquals(1L, out.get(0).getId());
    }

    @Test
    void trending_empty_throws() {
        when(repo.findAll()).thenReturn(List.of());
        assertThrows(Exception.class, () -> service.trending());
    }
}
