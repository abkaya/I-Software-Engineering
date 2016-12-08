package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.QuestionsSurvey;
import be.uantwerpen.fti.se.model.Survey;
import be.uantwerpen.fti.se.repository.QuestionsSurveyRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import be.uantwerpen.fti.se.service.QuestionsSurveyService;
import be.uantwerpen.fti.se.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Kevin on 8/12/2016.
 */
@Controller
public class CreateSurveyController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionsSurveyRepository questionsSurveyRepository;
    @Autowired
    private QuestionsSurveyService questionsSurveyService;

    @RequestMapping(value="/createsurvey", method = RequestMethod.GET)
    public String showResultSurvey1(final ModelMap model) {
        model.addAttribute("allQuestions",questionsSurveyRepository.findAll());
        model.addAttribute("SurveyCreateActiveSettings", "active");
        return "qsurvey-list";
    }

    @RequestMapping(value="/createsurvey/put", method= RequestMethod.GET)
    public String viewCreateQuestion(Principal principal, final ModelMap model){
            model.addAttribute("allUsers", userRepository.findAll());
            model.addAttribute("qsurvey", new QuestionsSurvey());
            model.addAttribute("SurveyCreateActiveSettings","active");
            return "survey-manage";
    }

    @RequestMapping(value={"/createsurvey/", "/createsurvey/{id}"}, method= RequestMethod.POST)
    public String addQuestion(Principal principal, @Valid QuestionsSurvey questionsSurvey, BindingResult result, final ModelMap model){
        if(userRepository.findByUserName(principal.getName()).isAdmin()) {
            if (result.hasErrors()) {
                model.addAttribute("allUsers", userRepository.findAll());
                return "survey-manage";
            }
            questionsSurveyService.saveSomeAttributes(questionsSurvey);
            return "redirect:/resultssurvey";
        }
        return "redirect:/";
    }
}
