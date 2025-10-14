package teamszg.initialisation_project.models;


import jakarta.persistence.*;

@Entity
@Table(name = "ratingEpisode")
public class RatingEpisode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Series series;
    @ManyToOne
    private Episode episode;
    @ManyToOne
    private Person person;
    private double rating;

    public int getId() {
        return id;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
