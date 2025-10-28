// src/test/java/models/RatingSerieModelTest.java
package services;

import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.models.Series;

import static org.junit.jupiter.api.Assertions.*;

public class RatingSerieModelTest {

    @Test
    void wiring_rating_serie() {
        Person p = new Person(); p.setId(5L); p.setName("Eve");
        Series s = new Series(); s.setId(8L); s.setTitle("Drama X");

        RatingSerie rs = new RatingSerie();
        rs.setId(101L);
        rs.setPerson(p);
        rs.setSeries(s);
        rs.setRating(9.0);

        assertEquals(101L, rs.getId());
        assertEquals(5L, rs.getPerson().getId());
        assertEquals(8L, rs.getSeries().getId());
        assertEquals(9.0, rs.getRating());
    }

    @Test
    void toString_not_empty() {
        RatingSerie r = new RatingSerie();
        r.setId(15L);
        assertNotNull(r.toString());
        assertTrue(r.toString().length() > 0);
    }
}
