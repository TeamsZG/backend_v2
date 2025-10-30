package teamszg.initialisation_project.models;


import jakarta.persistence.*;

/**
 * Représente une évaluation (rating) attribuée par une personne à une série entière.
 * <p>
 * Cette entité est persistée dans la table "ratingSerie" de la base de données.
 * Elle permet de relier un {@link Person} à une {@link Series} avec une note numérique (double).
 * </p>
 */

@Entity
@Table(name = "ratingSerie")
public class RatingSerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person person;
    @ManyToOne
    private Series series;
    private double rating;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
