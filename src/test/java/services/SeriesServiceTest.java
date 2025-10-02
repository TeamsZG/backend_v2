package teamszg.initialisation_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
}
