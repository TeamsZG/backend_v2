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

@Repository
public interface IRatingSerieRepository extends JpaRepository<RatingSerie,Long> {
    List<RatingEpisode> findByPerson(Person person);
    List<RatingEpisode> findBySeries(Series series);
    RatingSerie findByPersonAndSeries(Person person, Series series);
    @Query("SELECT AVG(rating.rating) FROM RatingSerie rating WHERE rating.series.id = :seriesId")
    Double averageBySeries(@Param("seriesId") Long seriesId);

}
