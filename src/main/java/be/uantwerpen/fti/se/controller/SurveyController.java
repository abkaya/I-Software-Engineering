package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Survey;
import be.uantwerpen.fti.se.repository.SurveyRepository;
import be.uantwerpen.fti.se.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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
    public String showResultSurvey(final ModelMap model){
        model.addAttribute("allOpinions", surveyRepository.findAll());
        model.addAttribute("SurveyActiveSettings","active");
        return "surveys-list";
    }

    @RequestMapping(value="/questionssurvey", method = RequestMethod.GET)
    public String showQuestionsSurvey(final ModelMap model){
        model.addAttribute("SurveyQActiveSettings","active");
        return "survey-questions";
    }

    @RequestMapping(value="/questionssurvey/{id}", method= RequestMethod.GET)
    public String viewEditTestSequence(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allOpinions", surveyRepository.findAll());
        model.addAttribute("opinion",surveyRepository.findOne(id));

        //Set the navigation button Test Management to active
        model.addAttribute("SurveyQActiveSettings","active");
        return "redirect:/home";
    }

    @RequestMapping(value={"/questionssurvey/", "/questionssurvey/{id}"}, method= RequestMethod.POST)
    public String addopin(@Valid Survey survey, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allOpinions", surveyRepository.findAll());
            //Set the navigation button User Management to active
            model.addAttribute("SurveyQActiveSettings","active");
            return "survey-questions";
        }
        surveyService.saveSomeAttributes(survey);
        return "redirect:/home";
    }

}
