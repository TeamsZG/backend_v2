package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.RatingSerie;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.IRatingSerieRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;

/**
 * Service pour gérer les notes (ratings) des séries.
 * <p>
 * Permet d'ajouter ou mettre à jour un rating pour une série,
 * et de récupérer la moyenne des notes d'une série.
 * </p>
 */

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

    public RatingSerie addRatingSerie(Long personId, Long seriesId, double rating) {
        Person person = personRepository.findPersonById(personId);
        Series series = seriesRepository.findSeriesById(seriesId);
        if (person == null || series == null) {
            throw new RuntimeException("Serie ou personne non existante");
        }
        if (rating < 0 || rating > 10) {
            throw new RuntimeException("Rating non valide");
        }
        RatingSerie verificationRatingSerie = ratingSerieRepository.findByPersonAndSeries(person, series);
        // ici on regarde si on peut pas faire directement modifier le rating déjà fait
        if (verificationRatingSerie != null) {
            verificationRatingSerie.setRating(rating);
        } else {
            // sinon on va faire le rating
            verificationRatingSerie = new RatingSerie();
            verificationRatingSerie.setPerson(person);
            verificationRatingSerie.setSeries(series);
            verificationRatingSerie.setRating(rating);
        }
        // on va enregistrer ce qu'il ce passe dans les 2 cas
        ratingSerieRepository.save(verificationRatingSerie);

        // Système de caclul de moyenne pour l'intégrer directement dans Serie
        Double moyenneSerieRating = ratingSerieRepository.moyenneParSerie(seriesId);
        if (moyenneSerieRating != null) {
            series.setNote(moyenneSerieRating);
            seriesRepository.save(series);
        } else {
            throw new RuntimeException("Aucune moyenne/note trouvée pour cette série.");
        }

        return verificationRatingSerie;
    }

    public double getRatingSerie(Long seriesId) {
        Double moyenneSerieRating = ratingSerieRepository.moyenneParSerie(seriesId);

        if (moyenneSerieRating == null) {
            throw new RuntimeException("Aucune note trouvée pour cette série.");
        }
        return moyenneSerieRating;
    }

}
