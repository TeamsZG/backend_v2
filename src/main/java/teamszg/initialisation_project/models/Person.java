package teamszg.initialisation_project.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int age;

    private String gender;


    @ManyToMany
    @JoinTable(
            name = "historique",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "series_id")
    )

    // Comme ca on évite les doublons
    private Set<Series> history = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Series> getHistory() {
        return history;
    }

    public void setHistory(Set<Series> history) {
        this.history = history;
    }

    // Source manyToMany : https://www.geeksforgeeks.org/advance-java/jpa-many-to-many-mapping/

}
