// src/test/java/models/PersonModelTest.java
package services;

import org.junit.jupiter.api.Test;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonModelTest {


    @Test
    void toString_not_empty() {
        Person p = new Person();
        p.setId(1L);
        assertNotNull(p.toString());
        assertTrue(p.toString().length() > 0);
    }
}
