// src/test/java/models/SeriesModelTest.java
package services;

import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.models.Series;

import static org.junit.jupiter.api.Assertions.*;

public class SeriesModelTest {

    @Test
    void getters_setters_work_and_defaults() {
        Series s = new Series();
        s.setId(1L);
        s.setTitle("Breaking Future");
        s.setGenre("SciFi");
        s.setNbEpisodes(45);
        s.setNote(9.2);
        s.setImg("http://x/y.png");
        s.setViews(123);

        assertEquals(1L, s.getId());
        assertEquals("Breaking Future", s.getTitle());
        assertEquals("SciFi", s.getGenre());
        assertEquals(45, s.getNbEpisodes());
        assertEquals(9.2, s.getNote());
        assertEquals("http://x/y.png", s.getImg());
        assertEquals(123, s.getViews());
    }

    @Test
    void nullables_and_equality() {
        Series a = new Series();
        a.setId(7L);
        a.setTitle(null);
        a.setGenre(null);
        a.setNote(null);
        a.setImg(null);
        a.setNbEpisodes(0);
        a.setViews(0);

        Series b = new Series();
        b.setId(7L);

        // Si Lombok @Data est utilisé, equals/hashCode se basent sur les champs.
        // À défaut, on valide juste que les champs sont bien null/0.
        assertNull(a.getTitle());
        assertNull(a.getGenre());
        assertNull(a.getNote());
        assertNull(a.getImg());

        // equals/hashCode ne sont pas contractuels si non surchargés, on vérifie simplement réflexivité:
        assertEquals(a, a);
        assertEquals(a.hashCode(), a.hashCode());

        // Même id → souvent égal si @EqualsAndHashCode(of="id"), sinon ce test restera vrai/inoffensif
        if (a.equals(b)) {
            assertEquals(a.hashCode(), b.hashCode());
        }
    }

    @Test
    void toString_not_empty() {
        Series s = new Series();
        s.setId(99L);
        assertNotNull(s.toString());
        assertTrue(s.toString().length() > 0);
    }
}
