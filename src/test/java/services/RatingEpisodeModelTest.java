// src/test/java/models/RatingEpisodeModelTest.java
package services;

import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.models.Episode;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.Series;

import static org.junit.jupiter.api.Assertions.*;

public class RatingEpisodeModelTest {

    @Test
    void wiring_rating_episode() {
        Person person = new Person(); person.setId(1L); person.setName("Bob");
        Series series = new Series(); series.setId(3L); series.setTitle("SciShow");
        Episode ep    = new Episode(); ep.setId(9L); ep.setTitle("S01E01"); ep.setSeries(series);

        RatingEpisode re = new RatingEpisode();
        re.setId(77);
        re.setPerson(person);
        re.setSeries(series);
        re.setEpisode(ep);
        re.setRating(7.5);

        assertEquals(77, re.getId());
        assertEquals(1L, re.getPerson().getId());
        assertEquals(3L, re.getSeries().getId());
        assertEquals(9L, re.getEpisode().getId());
        assertEquals(7.5, re.getRating());
    }

    @Test
    void toString_not_empty() {
        RatingEpisode r = new RatingEpisode();
        r.setId(11);
        assertNotNull(r.toString());
        assertTrue(r.toString().length() > 0);
    }
}
