package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Series;

import java.util.List;

public interface ISeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByGenre(String genre);
}