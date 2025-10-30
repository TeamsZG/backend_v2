package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teamszg.initialisation_project.models.Series;

import java.util.List;

/**
 * Interface de repository pour gérer les requêtes spécifiques aux recommandations.
 * <p>
 * Cette interface étend {@link JpaRepository} pour bénéficier des méthodes CRUD sur {@link Series}.
 * Elle inclut également des requêtes personnalisées pour obtenir les genres les plus populaires.
 * </p>
 */

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