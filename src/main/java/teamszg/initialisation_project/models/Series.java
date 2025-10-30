package teamszg.initialisation_project.models;


import jakarta.persistence.*;

/**
 * Représente une série télévisée ou web série.
 * <p>
 * Cette entité est persistée dans la table "series" de la base de données.
 * Elle contient les informations principales sur une série : titre, genre, nombre d’épisodes, note moyenne, image et nombre de vues.
 * </p>
 */

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private int nbEpisodes;
    @Column(nullable = true)
    private Double note;
    private String img;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int views;
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }



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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNbEpisodes() {
        return nbEpisodes;
    }

    public void setNbEpisodes(int nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
