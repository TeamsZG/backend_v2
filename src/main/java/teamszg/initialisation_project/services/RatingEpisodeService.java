package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Episode;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingEpisode;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IEpisodeRepository;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.IRatingEpisodeRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;


@Service
public class RatingEpisodeService {
    private IRatingEpisodeRepository ratingEpisodeRepository;
    private ISeriesRepository seriesRepository;
    private IPersonRepository personRepository;
    private IEpisodeRepository episodeRepository;




    public RatingEpisodeService(IRatingEpisodeRepository ratingEpisodeRepository, ISeriesRepository seriesRepository, IPersonRepository personRepository,  IEpisodeRepository episodeRepository) {

        this.ratingEpisodeRepository = ratingEpisodeRepository;
        this.seriesRepository = seriesRepository;
        this.personRepository = personRepository;
        this.episodeRepository = episodeRepository;


    }
    public RatingEpisode addRatingEpisode(Long personId, Long episodeId, double rating) {
        Person person = personRepository.findPersonById(personId);
        Episode episode = episodeRepository.findEpisodeById(episodeId);

        if (episode == null || person == null) {
            throw new RuntimeException("Episode ou personne non existant(e)");
        }
        if(rating < 0 || rating > 10){
            throw new RuntimeException("Rating non valide");
        }

        Series series = episode.getSeries();

        RatingEpisode ratingEpisode = ratingEpisodeRepository.findRatingEpisodeByEpisodeAndSeriesAndPerson(episode, series, person);

        if (ratingEpisode != null) {
            ratingEpisode.setRating(rating);
        } else {
            ratingEpisode = new RatingEpisode();
            ratingEpisode.setEpisode(episode);
            ratingEpisode.setSeries(series);
            ratingEpisode.setPerson(person);
            ratingEpisode.setRating(rating);
        }

        ratingEpisodeRepository.save(ratingEpisode);

        Double moyenneEpisode = ratingEpisodeRepository.moyenneParEpisode(episodeId);
        episode.setRating(moyenneEpisode);
        episodeRepository.save(episode);

        return ratingEpisode;
    }



    // Un peu redodant parce que ce qu'on peut faire c'est de juste get avec l'objet episode sa moyenne
    // Vu que le code en haut fait l'enregsitrement
    // Par contre ce que ca peut nous aider c'est si on fait des faux information cela ne va pas le prendre en compte.
            // Exemple je post un episode et je dis qu'il a 5 de rate. Si je get il va dire qu'il n'a pas de valeur
            // Dit moi quelle option choisir
    public double getRatingEpisode(Long episodeId) {
        Double moyenne = ratingEpisodeRepository.moyenneParEpisode(episodeId);

        if (moyenne == null) {
            throw new RuntimeException("Aucune note trouvée pour cet épisode.");
        }

        return moyenne;
    }
}
