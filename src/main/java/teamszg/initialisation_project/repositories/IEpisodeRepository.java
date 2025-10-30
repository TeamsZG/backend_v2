package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Episode;

/**
 * Interface de repository pour gérer la persistance des {@link Episode}.
 * <p>
 * Cette interface étend JpaRepository pour bénéficier des méthodes CRUD de Spring Data JPA.
 * Elle permet également de définir des méthodes de recherche personnalisées.
 * </p>
 */

public interface IEpisodeRepository extends JpaRepository<Episode, Long> {
    Episode findEpisodeById(Long episodeId);

}
