package teamszg.initialisation_project.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private int nbEpisodes;
    private double note;
}
