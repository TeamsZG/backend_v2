package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamszg.initialisation_project.models.Series;

import java.util.List;

/**
 * Interface de repository pour gérer la persistance des {@link Series}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour bénéficier des méthodes CRUD et
 * fournit des méthodes de recherche personnalisées pour filtrer les séries selon différents critères.
 * </p>
 */

@Repository
public interface ISeriesRepository extends JpaRepository<Series, Long> {
    Series findSeriesById(Long id);
    List<Series> findSeriesByGenreIgnoreCase(String genre);
    List<Series> findSeriesBytitleIgnoreCase(String title);
    List<Series> findSeriesByNbEpisodesGreaterThanEqual(int nbEpisodes);
}