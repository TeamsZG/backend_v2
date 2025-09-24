package teamszg.initialisation_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teamszg.initialisation_project.models.Series;
import teamszg.initialisation_project.services.RecommandationsService;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class RecommandationController {
    @Autowired
    private RecommandationsService recommandationsService;

    @GetMapping("/top3genre")
    public List<Object[]> searchByName() throws Exception {
        return recommandationsService.getBestGenre();
    }

    @GetMapping("/{id}/recommendations")
    public List<Series> getRecommandation(@PathVariable Long id) throws Exception {
        return recommandationsService.findRecommandations(id);
    }

    @GetMapping("/{id}/recommendations/criteria")
    public List<Map<String, Object>> getCriteriaRecommendations(@PathVariable Long id) throws Exception {
        return recommandationsService.getRecommendationsByCriteria(id);
    }

    @GetMapping("/{id}/recommendations/similar")
    public Map<String, Object> getSimilarRecommendations(@PathVariable Long id) throws Exception {
        return recommandationsService.getRecommendationsBySimilarUsers(id);
    }


}
