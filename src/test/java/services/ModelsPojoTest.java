// src/test/java/models/ModelsPojoTest.java
package services;

import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.models.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ModelsPojoTest {

    @Test
    void series_getters_setters() {
        Series s = new Series();
        s.setId(1L);
        s.setTitle("T");
        s.setGenre("G");
        s.setNbEpisodes(10);
        s.setNote(8.0);
        s.setViews(123);
        s.setImg("url");
        assertEquals(1L, s.getId());
        assertEquals("T", s.getTitle());
        assertEquals("G", s.getGenre());
        assertEquals(10, s.getNbEpisodes());
        assertEquals(8.0, s.getNote());
        assertEquals(123, s.getViews());
        assertEquals("url", s.getImg());
    }

    @Test
    void person_getters_setters() {
        Person p = new Person();
        p.setId(2L); p.setName("N"); p.setAge(30); p.setGender("M");
        p.setEmail("e@e"); p.setPasswordHash("hash");
        p.setHistory(new HashSet<>());
        assertEquals(2L, p.getId());
        assertEquals("N", p.getName());
        assertEquals(30, p.getAge());
        assertEquals("M", p.getGender());
        assertEquals("e@e", p.getEmail());
        assertEquals("hash", p.getPasswordHash());
        assertNotNull(p.getHistory());
    }

    @Test
    void episode_getters_setters() {
        Episode e = new Episode();
        e.setId(5L); e.setTitle("E"); e.setDescription("D");
        e.setDuration(50); e.setRating(9.0);
        Series s = new Series(); s.setId(1L);
        e.setSeries(s);
        assertEquals(5L, e.getId());
        assertEquals("E", e.getTitle());
        assertEquals("D", e.getDescription());
        assertEquals(50, e.getDuration());
        assertEquals(9.0, e.getRating());
        assertEquals(1L, e.getSeries().getId());
    }

    @Test
    void ratingEpisode_getters_setters() {
        RatingEpisode r = new RatingEpisode();
        r.setId(7); r.setRating(8.5);
        r.setEpisode(new Episode());
        r.setPerson(new Person());
        r.setSeries(new Series());
        assertEquals(7, r.getId());
        assertEquals(8.5, r.getRating());
        assertNotNull(r.getEpisode());
        assertNotNull(r.getPerson());
        assertNotNull(r.getSeries());
    }

    @Test
    void ratingSerie_getters_setters() {
        RatingSerie r = new RatingSerie();
        r.setId(9L); r.setRating(7.0);
        r.setPerson(new Person());
        r.setSeries(new Series());
        assertEquals(9L, r.getId());
        assertEquals(7.0, r.getRating());
        assertNotNull(r.getPerson());
        assertNotNull(r.getSeries());
    }
}
