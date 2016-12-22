package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Survey;
import be.uantwerpen.fti.se.model.TestObject;
import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.repository.SurveyRepository;
import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.service.TestObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin Van Ravels on 26-Nov-16.
 */
@Controller
public class TestObjectController {

    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private TestObjectService testObjectService;
    @Autowired
    private TestSequenceRepository testSequenceRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @RequestMapping(value = "/testobjects", method = RequestMethod.GET)
    public String showTestObject(Principal principal, final ModelMap model){
        model.addAttribute("myTestObjects", testObjectService.findForUser(principal.getName()));
        model.addAttribute("testObjectsActiveSettings", "active");
        return "testobject-list";
    }

    @RequestMapping(value = "/testobjects/{id}", method = RequestMethod.GET)
    public String takeTest(@PathVariable Long id, Principal principal, final ModelMap model){

        Survey survey = new Survey();
        TestObject testObject = testObjectRepository.findOne(id);
        survey.setDevice(testObject.getTestPlan().getDevice().getDeviceName());
        survey.setUser(principal.getName());
        surveyRepository.save(survey);
        testObject.setSurvey(survey);
        testObjectRepository.save(testObject);


        //this function should start the test. As a placeholder dud results are created
        testObjectService.finishTest(testObjectRepository.findOne(id));

        List<TestSequence> sequences = new ArrayList<TestSequence>();
        for(Long seqID : testObjectRepository.findOne(id).getSequences()){
            sequences.add(testSequenceRepository.findOne(seqID));
        }
        model.addAttribute("allSequences", sequences);

        return "fitts-test";
    }

}
