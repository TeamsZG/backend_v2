package teamszg.initialisation_project.models;


import jakarta.persistence.*;

/**
 * Représente un épisode d'une série.
 * <p>
 * Cette entité est persistée dans la table "episode" de la base de données.
 * Chaque épisode est associé à une {@link Series}.
 * </p>
 */

@Entity
@Table(name= "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Column(nullable = true)
    private Double rating;
    private int duration;

    @ManyToOne
    @JoinColumn(name="series_id")
    private Series series;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }



}
