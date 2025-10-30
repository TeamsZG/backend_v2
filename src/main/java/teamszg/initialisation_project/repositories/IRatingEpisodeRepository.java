package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamszg.initialisation_project.models.Episode;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.Series;

/**
 * Interface de repository pour gérer la persistance des {@link RatingEpisode}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour bénéficier des méthodes CRUD de Spring Data JPA.
 * Elle inclut également des méthodes personnalisées pour récupérer et calculer les évaluations d'épisodes.
 * </p>
 */

@Repository
public interface IRatingEpisodeRepository extends JpaRepository<RatingEpisode,Long> {

    RatingEpisode findRatingEpisodeByEpisodeAndSeriesAndPerson(Episode episode, Series series, Person person);

    @Query("SELECT AVG(rate.rating) FROM RatingEpisode rate WHERE rate.episode.id = :episodeId")
    Double moyenneParEpisode(@Param("episodeId") Long episodeId);


}
