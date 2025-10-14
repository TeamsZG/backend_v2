package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamszg.initialisation_project.models.Episode;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.Series;

import java.util.List;

@Repository
public interface IRatingEpisodeRepository extends JpaRepository<RatingEpisode,Long> {
    List<RatingEpisode> findByPerson(Person person);
    List<RatingEpisode> findBySeries(Series series);
    List<RatingEpisode> findByEpisode(Episode episode);

    @Query("SELECT AVG(rate.rating) FROM RatingEpisode rate WHERE rate.episode.id = :episodeId")
    Double moyenneParEpisode(@Param("episodeId") Long episodeId);


}
