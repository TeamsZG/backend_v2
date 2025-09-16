package teamszg.initialisation_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import teamszg.initialisation_project.models.Series;


public interface ISeriesRepository extends JpaRepository<Series, Long> {
    Series findSeriesById(Long id);
}