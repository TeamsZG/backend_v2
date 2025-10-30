package services;

import teamszg.initialisation_project.InitialisationProjectApplication;
import teamszg.initialisation_project.controllers.SeriesController;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.SeriesService;
import teamszg.initialisation_project.security.JwtAuthFilter;
import teamszg.initialisation_project.security.JwtService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Point le test vers ta classe @SpringBootApplication
@ContextConfiguration(classes = InitialisationProjectApplication.class)
@WebMvcTest(
        controllers = SeriesController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = { JwtAuthFilter.class } // <-- empêche la création du filtre
        )
)
@AutoConfigureMockMvc(addFilters = false) // <-- n’applique pas les filtres de sécurité
class SeriesControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    // tes mocks existants
    @MockBean private SeriesService seriesService;

    // au cas où quelque chose autowire JwtService (rare ici)
    @MockBean private JwtService jwtService;

    private Series series1;
    private Series series2;

    @BeforeEach
    void setUp() {
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
    void testGetAllSeries() throws Exception {
        List<Series> allSeries = Arrays.asList(series1, series2);
        when(seriesService.findAllSeries()).thenReturn(allSeries);

        mockMvc.perform(get("/series"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Breaking Bad"))
                .andExpect(jsonPath("$[1].title").value("Friends"));
    }

    @Test
    void testGetSerieById() throws Exception {
        when(seriesService.findSeriesById(1L)).thenReturn(series1);

        mockMvc.perform(get("/series/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Breaking Bad"))
                .andExpect(jsonPath("$.genre").value("Crime"));
    }

    @Test
    void testAddSeries() throws Exception {
        Series newSeries = new Series();
        newSeries.setTitle("Game of Thrones");
        newSeries.setGenre("Fantasy");
        newSeries.setNbEpisodes(73);
        newSeries.setNote(9.3);

        when(seriesService.addSeries(any(Series.class))).thenReturn(newSeries);

        mockMvc.perform(post("/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSeries)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Game of Thrones"))
                .andExpect(jsonPath("$.genre").value("Fantasy"));
    }

    @Test
    void testUpdateSeries() throws Exception {
        Series updated = new Series();
        updated.setTitle("Breaking Bad Updated");
        updated.setGenre("Crime");
        updated.setNbEpisodes(63);
        updated.setNote(9.6);

        when(seriesService.updateSeries(any(Long.class), any(Series.class))).thenReturn(updated);

        mockMvc.perform(put("/series/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Breaking Bad Updated"))
                .andExpect(jsonPath("$.nbEpisodes").value(63));
    }

    @Test
    void testDeleteSeries() throws Exception {
        mockMvc.perform(delete("/series/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Supression de l'id serie: 1"));
    }

    @Test
    void testSearchSeriesByGenre() throws Exception {
        List<Series> filtered = Arrays.asList(series1);
        when(seriesService.search("Crime", null, null)).thenReturn(filtered);

        mockMvc.perform(get("/series/search")
                        .param("genre", "Crime"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Breaking Bad"))
                .andExpect(jsonPath("$[0].genre").value("Crime"));
    }


    @Test
    void testSearchByGenre_only_controller() throws Exception {
        Series s = new Series();
        s.setId(10L); s.setTitle("Horror Night"); s.setGenre("Horror"); s.setNbEpisodes(10);

        when(seriesService.search("Horror", null, null))
                .thenReturn(Arrays.asList(s));

        mockMvc.perform(get("/series/search").param("genre", "Horror"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Horror Night"));
    }


}
