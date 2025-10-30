package teamszg.initialisation_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.SeriesService;

import java.util.List;

/**
 * Contrôleur REST responsable de la gestion des séries.
 *
 * Il permet d’effectuer toutes les opérations CRUD (Create, Read, Update, Delete),
 * ainsi que des recherches et la récupération des séries populaires.
 */

@RestController
@CrossOrigin
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @GetMapping
    public List<Series> findAllSeries() throws Exception {
        return  seriesService.findAllSeries();
    }

    @GetMapping("/{id}")
    public Series findSeriesById(@PathVariable Long id) throws Exception {
        return seriesService.findSeriesById(id);
    }
    @PostMapping
    public Series addSeries(@RequestBody Series series) throws Exception{
        return seriesService.addSeries(series);
    }
    @PutMapping("/{id}")
    public Series updateSeries(@PathVariable Long id,  @RequestBody Series series) throws Exception{
        return seriesService.updateSeries(id,series);
    }
    @DeleteMapping("/{id}")
    public String deleteSeries(@PathVariable Long id) throws Exception{
        seriesService.deleteSeries(id);
        return "Supression de l'id serie: " + id ;
    }

    @GetMapping("/search")
    public List<Series> searchSeries(@RequestParam(required = false) String genre, @RequestParam(required = false) String title, @RequestParam(required = false) Integer minEpisode) throws Exception {
        return seriesService.search(genre, title, minEpisode);
    }

    @GetMapping("/trending")
    public List<Series> getTrendingSeries() throws Exception {
        return seriesService.trending();
    }

}
