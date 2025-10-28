// src/test/java/services/RecommandationControllerStandaloneTest.java
package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamszg.initialisation_project.controllers.RecommandationController;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.RecommandationsService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecommandationControllerStandaloneTest {

    private MockMvc mvc;
    private RecommandationsService recommandationsService;

    @BeforeEach
    void setup() throws Exception {
        recommandationsService = Mockito.mock(RecommandationsService.class);
        RecommandationController controller = new RecommandationController();

        Field f = RecommandationController.class.getDeclaredField("recommandationsService");
        f.setAccessible(true);
        f.set(controller, recommandationsService);

        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }



    @Test
    void recommendations_ok() throws Exception {
        Series s = new Series(); s.setId(1L); s.setTitle("X");
        when(recommandationsService.findRecommandations(5L)).thenReturn(List.of(s));

        mvc.perform(get("/user/5/recommendations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void recommendations_criteria_ok() throws Exception {
        Series s = new Series(); s.setId(7L); s.setTitle("Y");
        when(recommandationsService.getRecommendationsByCriteria(2L))
                .thenReturn(List.of(Map.of("series", s, "score", 8.5)));

        mvc.perform(get("/user/2/recommendations/criteria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].series.id").value(7))
                .andExpect(jsonPath("$[0].score").value(8.5));
    }

    @Test
    void recommendations_similar_ok() throws Exception {
        when(recommandationsService.getRecommendationsBySimilarUsers(3L))
                .thenReturn(Map.of("similarUser", Map.of("id", 99), "recommendations", List.of()));

        mvc.perform(get("/user/3/recommendations/similar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.similarUser.id").value(99));
    }
}
