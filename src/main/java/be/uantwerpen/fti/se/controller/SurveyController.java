package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.SurveyRepository;
import be.uantwerpen.fti.se.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Kevin on 24/11/2016.
 */
@Controller
public class SurveyController {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyService surveyService;

    @RequestMapping(value="/resultssurvey", method = RequestMethod.GET)
    public String showTestSequences(final ModelMap model){
        model.addAttribute("allOpinions", surveyRepository.findAll());
        //Set the navigation button Test Management to active
        model.addAttribute("SurveyActiveSettings","active");
        return "surveys-list";
    }
}
