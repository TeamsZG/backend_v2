package teamszg.initialisation_project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.services.RatingEpisodeService;
import teamszg.initialisation_project.services.RatingSerieService;

@RestController
@CrossOrigin
@RequestMapping("/ratings")
public class RatingController {


    @Autowired
    private RatingEpisodeService ratingEpisodeService;
    @Autowired
    private RatingSerieService ratingSerieService;


    // Pas le choix de faire les RquestParm vu que on a pas encore les tokens et le rating sa sera une logique dans le frontend

    @PostMapping("/episode/{episodeId}")
    public RatingEpisode PostRatingEpisode(@PathVariable Long episodeId, @RequestParam Long userId, @RequestParam double rating) {
        return ratingEpisodeService.addRatingEpisode(userId, episodeId, rating);
    }

    @GetMapping("/episode/{episodeId}")
    public double getMoyenneEpisodeRating(@PathVariable Long episodeId) {
        return ratingEpisodeService.getRatingEpisode(episodeId);
    }

    @PostMapping("/series/{seriesId}")
    public RatingSerie PostRatingSeries(@PathVariable Long seriesId, @RequestParam Long userId, @RequestParam double rating) {
        return ratingSerieService.addRatingSerie(userId, seriesId, rating);
    }
    @GetMapping("/series/{seriesId}")
    public double getMoyenneSeriesRating(@PathVariable Long seriesId) {
        return ratingSerieService.getRatingSerie(seriesId);
    }
}
