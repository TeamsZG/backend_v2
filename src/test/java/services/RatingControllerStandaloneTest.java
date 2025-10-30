// src/test/java/services/RatingControllerStandaloneTest.java
package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import teamszg.initialisation_project.controllers.RatingController;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.security.UserPrincipal;
import teamszg.initialisation_project.services.RatingEpisodeService;
import teamszg.initialisation_project.services.RatingSerieService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerStandaloneTest {

    private MockMvc mvc;
    private RatingEpisodeService ratingEpisodeService;
    private RatingSerieService ratingSerieService;

    @BeforeEach
    void setup() {
        ratingEpisodeService = Mockito.mock(RatingEpisodeService.class);
        ratingSerieService = Mockito.mock(RatingSerieService.class);

        RatingController controller = new RatingController();

        // inject services via reflection (fields sont @Autowired)
        try {
            var f1 = RatingController.class.getDeclaredField("ratingEpisodeService");
            f1.setAccessible(true);
            f1.set(controller, ratingEpisodeService);

            var f2 = RatingController.class.getDeclaredField("ratingSerieService");
            f2.setAccessible(true);
            f2.set(controller, ratingSerieService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Ajoute le resolver de @AuthenticationPrincipal
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .build();
    }

    private UsernamePasswordAuthenticationToken auth() {
        UserPrincipal principal = new UserPrincipal(99L, "user@test.com");
        return new UsernamePasswordAuthenticationToken(
                principal, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }


    @Test
    void getRatingEpisode_ok() throws Exception {
        when(ratingEpisodeService.getRatingEpisode(7L, 3L)).thenReturn(9.1);

        mvc.perform(get("/ratings/episode/7").param("seriesId","3"))
                .andExpect(status().isOk())
                .andExpect(content().string("9.1"));
    }



    @Test
    void getRatingSeries_ok() throws Exception {
        when(ratingSerieService.getRatingSerie(10L)).thenReturn(8.0);

        mvc.perform(get("/ratings/series/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("8.0"));
    }
}
