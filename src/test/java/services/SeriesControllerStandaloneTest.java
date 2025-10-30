// src/test/java/services/SeriesControllerStandaloneTest.java
package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamszg.initialisation_project.controllers.SeriesController;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.SeriesService;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SeriesControllerStandaloneTest {

    private MockMvc mvc;
    private SeriesService seriesService;
    private final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setup() throws Exception {
        seriesService = Mockito.mock(SeriesService.class);
        SeriesController controller = new SeriesController();

        Field f = SeriesController.class.getDeclaredField("seriesService");
        f.setAccessible(true);
        f.set(controller, seriesService);

        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll_ok() throws Exception {
        Series s = new Series(); s.setId(1L); s.setTitle("X");
        when(seriesService.findAllSeries()).thenReturn(List.of(s));

        mvc.perform(get("/series"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("X"));
    }

    @Test
    void findById_ok() throws Exception {
        Series s = new Series(); s.setId(3L); s.setTitle("Y");
        when(seriesService.findSeriesById(3L)).thenReturn(s);

        mvc.perform(get("/series/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Y"));
    }

    @Test
    void add_ok() throws Exception {
        Series in = new Series(); in.setTitle("New");
        Series out = new Series(); out.setId(9L); out.setTitle("New");
        when(seriesService.addSeries(any(Series.class))).thenReturn(out);

        mvc.perform(post("/series").contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9));
    }

    @Test
    void update_ok() throws Exception {
        Series in = new Series(); in.setTitle("Up");
        Series out = new Series(); out.setId(4L); out.setTitle("Up");
        when(seriesService.updateSeries(eq(4L), any(Series.class))).thenReturn(out);

        mvc.perform(put("/series/4").contentType(APPLICATION_JSON)
                        .content(om.writeValueAsBytes(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4));
    }

    @Test
    void delete_ok() throws Exception {
        mvc.perform(delete("/series/6"))
                .andExpect(status().isOk())
                .andExpect(content().string("Supression de l'id serie: 6"));
    }

    @Test
    void search_byGenreAndMin_ok() throws Exception {
        Series a = new Series(); a.setId(1L); a.setTitle("X"); a.setGenre("SciFi"); a.setNbEpisodes(50);
        when(seriesService.search(eq("SciFi"), isNull(), eq(40))).thenReturn(List.of(a));

        mvc.perform(get("/series/search").param("genre","SciFi").param("minEpisode","40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void trending_ok() throws Exception {
        Series a = new Series(); a.setId(1L); a.setTitle("Hot");
        when(seriesService.trending()).thenReturn(List.of(a));

        mvc.perform(get("/series/trending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Hot"));
    }
}
