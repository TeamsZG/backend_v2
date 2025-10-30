package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.IPersonRepository;
import teamszg.initialisation_project.repositories.IRecommendationRepository;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour gérer les recommandations de séries.
 * <p>
 * Fournit des recommandations basées sur l'historique de visionnage
 * d'un utilisateur, les genres les plus populaires et les utilisateurs similaires.
 * </p>
 */

@Service
public class RecommandationsService {

    private final IRecommendationRepository IrecommendationRepository;
    private final IPersonRepository personRepository;
    private static SeriesService seriesService;
    private final PersonService personService;
    private final ISeriesRepository seriesRepository;

    public RecommandationsService(IRecommendationRepository IRecommendationRepository, IPersonRepository personRepository, PersonService personService, ISeriesRepository seriesRepository) {
        this.IrecommendationRepository = IRecommendationRepository;
        this.personRepository = personRepository;
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


    public List<Map<String, Object>> getRecommendationsByCriteria(Long personId) throws Exception {

        // récupérer la personne et gérer l'erreur
        Person person = personService.getObjectPersonById(personId);
        if (person == null) throw new Exception("Personne non trouvée");

        // Récupérer ces genres préferer
        Set<String> genres = person.getHistory().stream()
                .map(s -> s.getGenre().toLowerCase())
                .collect(Collectors.toSet());

        List<Map<String, Object>> scoredSeries = new ArrayList<>();

        // https://www.baeldung.com/java-iterate-map-list
        //On parcours toutes les series et on crée un score selon les genre préferer et la note de la séries
        //pour données un score sur 10 a peut près
        for (Series s : seriesRepository.findAll()) {
            if (!person.getHistory().contains(s)) {
                double score = 0.0;

                // +5 si c'est dans le même genre
                if (genres.contains(s.getGenre().toLowerCase())) {
                    score += 5;
                }

                // + la note/2 pour pouvoir faire un score sur 10 a peu près
                score += s.getNote() / 2.0;

                // https://www.baeldung.com/java-iterate-map-list
                Map<String, Object> result = new HashMap<>();
                result.put("series", s);
                result.put("score", score);

                scoredSeries.add(result);

            }
        }

        // Trier par score décroissant pour de meilleurs recommandation
        scoredSeries.sort((a, b) -> Double.compare(
                (double) b.get("score"),
                (double) a.get("score")
        ));

        //On limite le recomm a 10 parce qu'au sinon il y aura des score très bas vu qu'on divise la note en 2

        List<Map<String, Object>> recommendations = scoredSeries.stream()
                .limit(10)
                .toList();

        return recommendations;
    }



    public Map<String, Object> getRecommendationsBySimilarUsers(Long personId) throws Exception {
        // récupérer la personne et gérer l'erreur
        Person person = personService.getObjectPersonById(personId);
        if (person == null) throw new Exception("Personne non trouvée");

        //trouver le user avec le plus de similaritude avec le user dont l'id est donné dans le controller
        Person similarUser = personRepository.findAll().stream()
                .filter(u -> !u.getId().equals(person.getId()))
                .max(Comparator.comparingInt(u ->
                        (int) person.getHistory().stream()
                                .filter(u.getHistory()::contains).count()
                ))
                .orElse(null);

        // https://medium.com/%40AlexanderObregon/javas-map-getordefault-method-explained-4fb86992e4b5
        //S'il n'y a pas de similar user cela gère l'erreur et rend juste un liste vide
        Map<String, Object> result = new HashMap<>();

        if (similarUser == null) {
            result.put("similarUser", null);
            result.put("recommendations", List.of());
            return result;
        }

        // Limité les recom a max 10
        List<Series> recommendations = similarUser.getHistory().stream()
                .filter(s -> !person.getHistory().contains(s))
                .limit(10)
                .toList();

        // afficher le similar user et son historique et les recommandation pour l'id donner dans la requête
        result.put("similarUser", similarUser);
        result.put("recommendations", recommendations);
        return result;
    }







}
