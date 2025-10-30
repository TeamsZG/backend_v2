package teamszg.initialisation_project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.models.Series;

import java.util.List;

/**
 * Interface de repository pour gérer la persistance des {@link RatingSerie}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour bénéficier des méthodes CRUD et
 * fournit des méthodes personnalisées pour récupérer les évaluations par personne ou par série,
 * ainsi que pour calculer la note moyenne d'une série.
 * </p>
 */

@Repository
public interface IRatingSerieRepository extends JpaRepository<RatingSerie,Long> {
    List<RatingEpisode> findByPerson(Person person);
    List<RatingEpisode> findBySeries(Series series);
    RatingSerie findByPersonAndSeries(Person person, Series series);
    @Query("SELECT AVG(rating.rating) FROM RatingSerie rating WHERE rating.series.id = :seriesId")
    Double moyenneParSerie(@Param("seriesId") Long seriesId);

}
