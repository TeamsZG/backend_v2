package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.IRatingSerieRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;

@Service
public class RatingSerieService {

    private IRatingSerieRepository ratingSerieRepository;
    private ISeriesRepository seriesRepository;
    private IPersonRepository personRepository;

    public RatingSerieService(IRatingSerieRepository ratingSerieRepository,  ISeriesRepository seriesRepository, IPersonRepository personRepository) {
        this.ratingSerieRepository = ratingSerieRepository;
        this.seriesRepository = seriesRepository;
        this.personRepository = personRepository;
    }

    public RatingSerie addRatingSerie(Long personId, Long seriesId, double rating){
        Person person = personRepository.findById(personId).orElse(null);
        Series series = seriesRepository.findById(seriesId).orElse(null);

        if(person == null || series == null){
            throw new RuntimeException("Serie ou personne non existante");
        }


        if(rating < 0 || rating > 10){
            throw new RuntimeException("Rating non valide");
        }

        RatingSerie verificationRatingSerie = ratingSerieRepository.findByPersonAndSeries(person, series);
        if(verificationRatingSerie != null){
            verificationRatingSerie.setRating(rating);
            return ratingSerieRepository.save(verificationRatingSerie);
        }
        RatingSerie ratingSerie = new RatingSerie();
        ratingSerie.setPerson(person);
        ratingSerie.setSeries(series);
        ratingSerie.setRating(rating);
        return ratingSerieRepository.save(ratingSerie);
    }



    public double getRatingSerie(Long seriesId) {
        Double moyenneSerieRating = ratingSerieRepository.moyenneParSerie(seriesId);

        if (moyenneSerieRating == null) {
            throw new RuntimeException("Aucune note trouvée pour cette série.");
        }
        return moyenneSerieRating;
    }

}
