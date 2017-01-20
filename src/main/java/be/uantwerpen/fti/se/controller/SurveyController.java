package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Survey;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.repository.SurveyRepository;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by Kevin on 24/11/2016.
 */
@Controller
public class SurveyController {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    SurveyService surveyService;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private TestObjectRepository testObjectRepository;

    @RequestMapping(value="/resultssurvey", method = RequestMethod.GET)
    public String showResultSurvey(final ModelMap model) {
        model.addAttribute("allOpinions", surveyRepository.findAll());
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("SurveyActiveSettings", "active");
        return "surveys-list";
    }

    @RequestMapping(value="/questionssurvey/{id}", method = RequestMethod.GET)
    public String showQuestionsSurvey(@PathVariable Long id, final ModelMap model, Principal principal){
        model.addAttribute("survey", testObjectRepository.findOne(id).getSurvey());
        System.out.println(testObjectRepository.findOne(id).getSurvey().getUser());
        //model.addAttribute("allDevices", deviceRepository.findAll());

        model.addAttribute("SurveyQActiveSettings","active");
        return "survey-questions";
    }

   /* @RequestMapping(value="/questionssurvey/{id}", method= RequestMethod.GET)
    public String viewEditSurvey(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allOpinions", surveyRepository.findAll());
        model.addAttribute("opinion",surveyRepository.findOne(id));
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("SurveyQActiveSettings","active");
        return "redirect:/home";
    }*/

    @RequestMapping(value={"/questionssurvey/", "/questionssurvey/{id}"}, method= RequestMethod.POST)
    public String addOpinion(@Valid Survey survey, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allOpinions", surveyRepository.findAll());
            model.addAttribute("allDevices", deviceRepository.findAll());
            model.addAttribute("SurveyQActiveSettings","active");
            return "survey-questions";
        }
        System.out.println(survey.getUser());
        System.out.println(survey.getDevice());
       // survey.setUser(testObjectRepository.findOne(id).getUser());
       // survey.setDevice(testObjectRepository.findOne(id).getTestPlan().getDevice().getDeviceName());
        surveyService.saveSomeAttributes(survey);
        return "redirect:/home";
    }

}
