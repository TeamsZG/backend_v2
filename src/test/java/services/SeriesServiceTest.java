package teamszg.initialisation_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeriesServiceTest {

    @Mock
    private ISeriesRepository seriesRepository;

    @InjectMocks
    private SeriesService seriesService;

    private Series series1;
    private Series series2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        series1 = new Series();
        series1.setId(1L);
        series1.setTitle("Breaking Bad");
        series1.setGenre("Crime");
        series1.setNbEpisodes(62);
        series1.setNote(9.5);

        series2 = new Series();
        series2.setId(2L);
        series2.setTitle("Friends");
        series2.setGenre("Comedy"); 
        series2.setNbEpisodes(236);
        series2.setNote(8.9);
    }


    @Test
    void testFindAllSeries() throws Exception {
        // Arrange
        List<Series> mockSeries = Arrays.asList(series1, series2);
        when(seriesRepository.findAll()).thenReturn(mockSeries);

        // Act
        List<Series> result = seriesService.findAllSeries();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Breaking Bad", result.get(0).getTitle());
        assertEquals("Friends", result.get(1).getTitle());

    }


    @Test
    void testFindSeriesById() throws Exception {
        when(seriesRepository.findSeriesById(1L)).thenReturn(series1);
        Series result = seriesService.findSeriesById(1L);

        assertEquals("Breaking Bad", result.getTitle());

    }

    @Test
    void testFindSeriesByIdNotFound() {
        when(seriesRepository.findSeriesById(3L)).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> seriesService.findSeriesById(3L));

        assertEquals("La series avec l'ID 3 n'a pas été trouvée.", exception.getMessage());

    }

    @Test
    void testAddSeries() throws Exception {
        when(seriesRepository.save(series1)).thenReturn(series1);
        Series result = seriesService.addSeries(series1);
        assertEquals(series1, result);

    }

    @Test
    void testUpdateSeries() throws Exception {
        Series updatedSeries = new Series();
        updatedSeries.setTitle("Breaking Bad Updated");
        updatedSeries.setGenre("Crime");
        updatedSeries.setNbEpisodes(63);
        updatedSeries.setNote(9.6);

        when(seriesRepository.findSeriesById(1L)).thenReturn(series1);
        when(seriesRepository.save(any(Series.class))).thenReturn(updatedSeries);
        Series result = seriesService.updateSeries(1L, updatedSeries);

        assertEquals("Breaking Bad Updated", result.getTitle());
        assertEquals(63, result.getNbEpisodes());
    }

    @Test
    void testDeleteSeries() throws Exception {
        when(seriesRepository.findSeriesById(1L)).thenReturn(series1);

        seriesService.deleteSeries(1L);

        verify(seriesRepository).delete(series1);
    }


    @Test
    void testSearchByGenre() throws Exception {

        series1.setId(1L);
        series1.setTitle("Ghost Hunters");
        series1.setGenre("Horror");
        series1.setNbEpisodes(12);

        Series series2 = new Series();
        series2.setId(2L);
        series2.setTitle("Ghost Adventures");
        series2.setGenre("Horror");
        series2.setNbEpisodes(8);

        when(seriesRepository.findAll()).thenReturn(Arrays.asList(series1, series2));
        when(seriesRepository.findSeriesByGenreIgnoreCase("Horror")).thenReturn(Arrays.asList(series1, series2));
        List<Series> result = seriesService.search("Horror", null, null);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getGenre().equalsIgnoreCase("Horror")));

    }
 

    @Test
    void testSearchWithInvalidFilter() {
        Exception exception = assertThrows(Exception.class, () -> seriesService.search(null, "", 0));
        assertEquals("Oups filtre non validé", exception.getMessage());
    }






    @Test
    void testSearchByTitle_only() throws Exception {
        Series a = new Series();
        a.setId(1L); a.setTitle("Ghost Hunters"); a.setGenre("Horror"); a.setNbEpisodes(12);

        Series b = new Series();
        b.setId(2L); b.setTitle("Space Ghost"); b.setGenre("SciFi"); b.setNbEpisodes(8);

        // IMPORTANT: listes mutables + mêmes instances partout
        List<Series> all = new ArrayList<>(Arrays.asList(a, b));
        when(seriesRepository.findAll()).thenReturn(all);
        when(seriesRepository.findSeriesBytitleIgnoreCase("Ghost"))
                .thenReturn(new ArrayList<>(Arrays.asList(a, b)));

        List<Series> result = seriesService.search(null, "Ghost", null);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(a, b)));
    }

    @Test
    void testSearchByMinEpisodes_only() throws Exception {
        Series a = new Series();
        a.setId(1L); a.setTitle("Short Show"); a.setGenre("Drama"); a.setNbEpisodes(5);

        Series b = new Series();
        b.setId(2L); b.setTitle("Long Show"); b.setGenre("Drama"); b.setNbEpisodes(20);

        List<Series> all = new ArrayList<>(Arrays.asList(a, b));
        when(seriesRepository.findAll()).thenReturn(all);
        when(seriesRepository.findSeriesByNbEpisodesGreaterThanEqual(10))
                .thenReturn(new ArrayList<>(List.of(b)));

        List<Series> result = seriesService.search(null, null, 10);

        assertEquals(1, result.size());
        assertSame(b, result.get(0));
    }

    @Test
    void testSearchCombined_genre_title_minEpisodes() throws Exception {
        Series a = new Series();
        a.setId(1L); a.setTitle("Crime Files"); a.setGenre("Crime"); a.setNbEpisodes(8);
        Series b = new Series();
        b.setId(2L); b.setTitle("Mega Crime Saga"); b.setGenre("Crime"); b.setNbEpisodes(50);

        List<Series> all = new ArrayList<>(Arrays.asList(a, b));
        when(seriesRepository.findAll()).thenReturn(all);

        when(seriesRepository.findSeriesByGenreIgnoreCase("Crime"))
                .thenReturn(new ArrayList<>(Arrays.asList(a, b))); // garde les 2
        when(seriesRepository.findSeriesBytitleIgnoreCase("Crime"))
                .thenReturn(new ArrayList<>(Arrays.asList(a, b))); // garde les 2
        when(seriesRepository.findSeriesByNbEpisodesGreaterThanEqual(10))
                .thenReturn(new ArrayList<>(List.of(b)));          // garde b seulement

        List<Series> result = seriesService.search("Crime", "Crime", 10);

        assertEquals(1, result.size());
        assertSame(b, result.get(0));
    }

    @Test
    void testUpdateSeries_notFound_throws() {
        when(seriesRepository.findSeriesById(99L)).thenReturn(null);
        Series payload = new Series();
        payload.setTitle("X"); payload.setGenre("Y"); payload.setNbEpisodes(1);
        Exception ex = assertThrows(Exception.class, () -> seriesService.updateSeries(99L, payload));
        assertTrue(ex.getMessage().toLowerCase().contains("n'a pas été trouvée"));
    }

    @Test
    void testDeleteSeries_notFound_throws() {
        when(seriesRepository.findSeriesById(123L)).thenReturn(null);
        Exception ex = assertThrows(Exception.class, () -> seriesService.deleteSeries(123L));
        assertTrue(ex.getMessage().toLowerCase().contains("n'a pas été trouvée"));
    }

}
