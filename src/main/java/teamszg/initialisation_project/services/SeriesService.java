package teamszg.initialisation_project.services;

import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.List;

@Service
public class SeriesService {

    private final ISeriesRepository seriesRepository;

    public SeriesService(ISeriesRepository seriesRepository) throws Exception {
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


    public List<Series> search(String genre, String title, Integer nbEpisodes) throws Exception {

        if ((genre == null || genre.isEmpty()) && (title == null || title.isEmpty()) && (nbEpisodes == null || nbEpisodes <= 0)) {
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

        if (nbEpisodes != null && nbEpisodes > 0) {
            List<Series> nbEpisodesList = seriesRepository.findSeriesByNbEpisodesGreaterThanEqual(nbEpisodes);
            allSeries.retainAll(nbEpisodesList);
        }

        return allSeries;
    }



// Source retainAll https://www.w3schools.com/java/tryjava.asp?filename=demo_ref_arraylist_retainall
}
