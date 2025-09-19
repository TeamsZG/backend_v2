package teamszg.initialisation_project.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Series;

@Repository
public interface IRecommendationRepository extends JpaRepository<Series, Long> {


    // Testé directement sur sqlLite
    @Query(value = "SELECT series.genre, COUNT(*) AS vue_total\n" +
            "FROM historique \n" +
            "JOIN series ON historique.series_id = series.id\n" +
            "GROUP BY series.genre\n" +
            "ORDER BY vue_total DESC\n" +
            "LIMIT 3;\n", nativeQuery = true)
    List<Object[]> top3Genre();
}

// source : https://www.data-bird.co/blog/jointures-sql