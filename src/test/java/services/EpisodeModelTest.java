// src/test/java/models/EpisodeModelTest.java
package services;

import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.models.Episode;
import teamszg.initialisation_project.models.Series;

import static org.junit.jupiter.api.Assertions.*;

public class EpisodeModelTest {

    @Test
    void episode_fields_and_series_link() {
        Series series = new Series();
        series.setId(2L);
        series.setTitle("Love in Paris");

        Episode e = new Episode();
        e.setId(100L);
        e.setTitle("Ep1");
        e.setDescription("Intro");
        e.setDuration(42);
        e.setRating(null);        // peut être null au départ
        e.setSeries(series);

        assertEquals(100L, e.getId());
        assertEquals("Ep1", e.getTitle());
        assertEquals("Intro", e.getDescription());
        assertEquals(42, e.getDuration());
        assertNull(e.getRating());
        assertNotNull(e.getSeries());
        assertEquals(2L, e.getSeries().getId());

        e.setRating(8.0);
        assertEquals(8.0, e.getRating());
    }

    @Test
    void toString_not_empty() {
        Episode e = new Episode();
        e.setId(5L);
        assertNotNull(e.toString());
        assertTrue(e.toString().length() > 0);
    }
}
