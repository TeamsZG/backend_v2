package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.List;

/**
 * Service pour gérer les séries.
 * <p>
 * Fournit des méthodes pour récupérer, créer, mettre à jour, supprimer,
 * rechercher et obtenir les séries tendance.
 * </p>
 */

@Service
public class SeriesService {

    private final ISeriesRepository seriesRepository;


    public SeriesService(ISeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }


    public List<Series> findAllSeries() throws Exception{
        List<Series> seriesList = seriesRepository.findAll();
        if(seriesList.isEmpty()){
            System.out.println("Pas de series dans la BD");
        }
        return seriesList;
    }

    public Series findSeriesById(Long id) throws Exception{
        Series serie = seriesRepository.findSeriesById(id);
        if(serie == null){
            throw new Exception("La series avec l'ID " + id + " n'a pas été trouvée.");
        }
        return serie;
    }

    public Series updateSeries(Long id, Series series) throws Exception{
        Series serie = seriesRepository.findSeriesById(id);
        if(serie == null){
            throw new Exception("La series avec l'ID " + id + " n'a pas été trouvée.");
        }
        serie.setGenre(series.getGenre());
        serie.setNote(series.getNote());
        serie.setNbEpisodes(series.getNbEpisodes());
        serie.setTitle(series.getTitle());

        return seriesRepository.save(serie);
    }

    public void deleteSeries(Long id) throws Exception{
        Series serieSupression = seriesRepository.findSeriesById(id);
        if(serieSupression == null){
            throw new Exception("La série  avec l'ID " + id +"n'a pas été trouvée.");
        }
        seriesRepository.delete(serieSupression);
    }

    public Series addSeries(Series series) throws Exception{
        return seriesRepository.save(series);
    }


    // Voici la fonction pour le search

    // Avec la méthode retainAll on par sur un principe de filtre successif.
    // En part de la totalité des séries et après chaque conditions de filtre elle devient de plus en plus petit

    public List<Series> search(String genre, String title, Integer minEpisodes) throws Exception {

        if ((genre == null || genre.isEmpty()) && (title == null || title.isEmpty()) && (minEpisodes == null || minEpisodes <= 0)) {
            throw new Exception("Oups filtre non validé");
        }

        List<Series> allSeries = seriesRepository.findAll();

        if (genre != null && !genre.isEmpty()) {
            List<Series> genreList = seriesRepository.findSeriesByGenreIgnoreCase(genre);
            allSeries.retainAll(genreList);
        }

        if (title != null && !title.isEmpty()) {
            List<Series> titleList = seriesRepository.findSeriesBytitleIgnoreCase(title);
            allSeries.retainAll(titleList);
        }

        if (minEpisodes  != null && minEpisodes > 0) {
            List<Series> nbEpisodesList = seriesRepository.findSeriesByNbEpisodesGreaterThanEqual(minEpisodes);
            allSeries.retainAll(nbEpisodesList);
        }

        return allSeries;
    }

    public List<Series> trending() throws Exception {
        List<Series> allSeries = seriesRepository.findAll();

        if (allSeries.isEmpty()) {
            throw new Exception("Aucune série trouvée dans la base de données.");
        }

        // Poids des critères (modifiable selon ton besoin)
        double facteurVues = 0.7;
        double facteurNote = 0.3;

        // Calcul du score de tendance pour chaque série
        allSeries.sort((s1, s2) -> {
            double score1 = s1.getViews() * facteurVues + (s1.getNote() != null ? s1.getNote() * facteurNote : 0);
            double score2 = s2.getViews() * facteurVues + (s2.getNote() != null ? s2.getNote() * facteurNote : 0);
            return Double.compare(score2, score1); // tri décroissant
        });

        // Retourne les 10 meilleures
        return allSeries.stream().limit(10).toList();
    }

// Source retainAll https://www.w3schools.com/java/tryjava.asp?filename=demo_ref_arraylist_retainall
}
