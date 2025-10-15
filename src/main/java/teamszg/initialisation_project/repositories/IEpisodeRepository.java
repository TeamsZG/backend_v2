package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Episode;

public interface IEpisodeRepository extends JpaRepository<Episode, Long> {
    Episode findEpisodeById(Long episodeId);

}
