package teamszg.initialisation_project.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente une personne utilisant l’application.
 * <p>
 * Cette entité est persistée dans la table "person" de la base de données.
 * Une personne possède un historique des séries regardées et des informations personnelles.
 * </p>
 */

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String gender;

    @Column(unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;
    @ManyToMany
    @JoinTable(
            name = "historique",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "series_id")
    )

    // Comme ca on évite les doublons
    private Set<Series> history = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Series> getHistory() {
        return history;
    }

    public void setHistory(Set<Series> history) {
        this.history = history;
    }

    // Source manyToMany : https://www.geeksforgeeks.org/advance-java/jpa-many-to-many-mapping/

}
