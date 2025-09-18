package teamszg.initialisation_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamszg.initialisation_project.models.Person;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.repositories.ISeriesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommandationsService {

    private static SeriesService seriesService;
    private static PersonService personService;
    private final ISeriesRepository seriesRepository;


    public RecommandationsService(SeriesService seriesService, PersonService personService, ISeriesRepository seriesRepository) {
        this.seriesService = seriesService;
        this.personService = personService;
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findRecommandations(Long id) throws Exception{

        List<Series> seriesList = seriesRepository.findAll();
        List<Series> seriesHistory = (List<Series>) personService.getAllHistory(id);
        List<Series> seriesRecomandations = new ArrayList<>();
        if(seriesList.isEmpty()){
            System.out.println("Pas de series dans la BD");
        }
        for (Series series : seriesList) {
            for (Series series2 : seriesHistory) {
                if(!series.getTitle().equals(series2.getTitle()) && series.getGenre().equals(series2.getGenre())){
                    seriesRecomandations.add(series);
                }
            }
        }
        return seriesRecomandations;
    }



}
