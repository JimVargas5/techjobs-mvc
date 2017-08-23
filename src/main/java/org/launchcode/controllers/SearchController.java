package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results")
    public String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        if (searchType.equalsIgnoreCase("all") && (searchTerm.equalsIgnoreCase(""))){
            ArrayList<HashMap<String, String>> relevantJobs = JobData.findAll();
            model.addAttribute("relevantJobs", relevantJobs);
            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("dumdum", "dumdum");
        } else if (searchType.equalsIgnoreCase("all") && !(searchTerm.equalsIgnoreCase(""))){
            ArrayList<HashMap<String, String>> allJobs = JobData.findAll();
            ArrayList<HashMap<String, String>> relevantJobs = new ArrayList<>();

            for (int i = 0; i < (allJobs.size()); i++) {
                for (Map.Entry<String, String> item : allJobs.get(i).entrySet()) {
                    if (item.getValue().toLowerCase().contains(searchTerm.toLowerCase())){
                        relevantJobs.add(allJobs.get(i));
                        break;
                    }
                }
            }

            model.addAttribute("relevantJobs", relevantJobs);
            model.addAttribute("columns", ListController.columnChoices);
        } else {
            ArrayList<HashMap<String, String>> relevantJobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("relevantJobs", relevantJobs);
            model.addAttribute("columns", ListController.columnChoices);
        }
        return "search";
    }
}
