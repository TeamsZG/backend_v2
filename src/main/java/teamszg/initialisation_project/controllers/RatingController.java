package teamszg.initialisation_project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.security.UserPrincipal;
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
    public RatingEpisode postRatingEpisode(
            @PathVariable Long episodeId,
            @RequestParam double rating,
            @RequestParam Long seriesId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (userPrincipal == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing token");
        return ratingEpisodeService.addRatingEpisode(userPrincipal.getId(), episodeId, rating, seriesId);
    }
    @GetMapping("/episode/{episodeId}")
    public double getMoyenneEpisodeRating(@PathVariable Long episodeId, @RequestParam Long seriesId ) {
        return ratingEpisodeService.getRatingEpisode(episodeId, seriesId);
    }

    @PostMapping("/series/{seriesId}")
    public RatingSerie postRatingSeries(
            @PathVariable Long seriesId,
            @RequestParam double rating,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (userPrincipal == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing token");
        return ratingSerieService.addRatingSerie(userPrincipal.getId(), seriesId, rating);
    }
    @GetMapping("/series/{seriesId}")
    public double getMoyenneSeriesRating(@PathVariable Long seriesId) {
        return ratingSerieService.getRatingSerie(seriesId);
    }
}
