package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IRecommendationRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RecommandationsService {

    private final IRecommendationRepository IrecommendationRepository;
    private static SeriesService seriesService;
    private final PersonService personService;
    private final ISeriesRepository seriesRepository;

    public RecommandationsService(IRecommendationRepository IRecommendationRepository, PersonService personService, ISeriesRepository seriesRepository) {
        this.IrecommendationRepository = IRecommendationRepository;
        this.personService = personService;
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findRecommandations(Long personId) throws Exception {

        // (COMMENTAIRE POUR ÉQUIPE) //

        // Ici on va récup la personne
        Person person = personService.getObjectPersonById(personId);
        if (person == null) {
            throw new Exception("Personne non trouvée");
        }

        // On va récupérer l'hisotrique de l'utilisateur comme ca on pourras comparé pour savoir s'il la déjà regardé ou pas
        // JSP s'il fonctionne complètement
        Set<Series> history = person.getHistory();

        // on va initilisé la liste ici pour le top3genre pour filtrer tout les series et recup que eux qui correspond a ca
        List<Object[]> top3genres = IrecommendationRepository.top3Genre();

        // La on va juste récupérer les noms en strings
        List<String> topGenres = new ArrayList<>();
        for (Object[] i : top3genres) {
            if (i[0] != null) {
                System.out.println(i[0]);
                topGenres.add(i[0].toString());
            }
        }

        List<Series> allSeries = seriesRepository.findAll();
        // les films qui n'ont pas encore été vus
        List<Series> listeSerieTop3 = new ArrayList<>(); // liste des séries faisant partie des top 3

        for(Series serie : allSeries){
            if(topGenres.contains((serie.getGenre()))){
                listeSerieTop3.add(serie);
            }
        }

        List<Series> series_a_recommander = new ArrayList<>();

        for(Series serietop3 : listeSerieTop3){
            if(!history.contains(serietop3)){
                series_a_recommander.add(serietop3);
            }
        }


        return series_a_recommander;
    }


    public List<Object[]> getBestGenre() {
        return IrecommendationRepository.top3Genre();
    }


}
