package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import be.uantwerpen.fti.se.service.TestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdil on 22/10/2016.
 */
@Controller
public class TestTemplateController {
    @Autowired
    private TestTemplateRepository testTemplateRepository;
    @Autowired
    private TestTemplateService testTemplateService;
    @Autowired
    private TestSequenceRepository testSequenceRepository;

    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    public String showTestTemplates(final ModelMap model) {
        //findAll is edited in the service, so it would refresh the number of tests per test template
        model.addAttribute("allTestTemplates", testTemplateService.findAll());
        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings", "active");
        return "testTemplates-list";
    }

    @RequestMapping(value = "/tests/put", method = RequestMethod.GET)
    public String viewCreateTestTemplate(final ModelMap model) {
        model.addAttribute("allTestSequences", testSequenceRepository.findAll());
        model.addAttribute("testTemplate", new TestTemplate("", ""));
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings", "active");
        return "testTemplates-manage";
    }

    @RequestMapping(value = "/tests/{id}", method = RequestMethod.GET)
    public String viewEditTestTemplate(@PathVariable Long id, final ModelMap model) {
        if (testTemplateRepository.findOne(id).isEditable()) {
            model.addAttribute("allTestSequences", testSequenceRepository.findAll());
            model.addAttribute("testTemplate", testTemplateRepository.findOne(id));

        /*
        //used to check whether or not the partial edits caused other attributes to turn to null - now fixed
        for(int i=0;i<50;i++) {
            System.out.println(testTemplateRepository.findOne(id).getTemplateDescription());
        }*/

            //Set the navigation button Test Management to active
            model.addAttribute("testsActiveSettings", "active");
            return "testTemplates-manage";
        }else{
            return "redirect:/tests";
    }}

    @RequestMapping(value = {"/tests/", "/tests/{id}"}, method = RequestMethod.POST)
    public String addTestTemplate(@Valid TestTemplate testTemplate, BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("allTestSequences", testSequenceRepository.findAll());
            return "testTemplates-manage";
        }
        if (testTemplate.isEditable()) {
            testTemplateService.saveSomeAttributes(testTemplate);
        }
        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings", "active");
        return "redirect:/tests/"+testTemplate.getId();
    }

    @RequestMapping(value = "/tests/{id}/copy")
    public String copyTestTemplate(@PathVariable Long id, final ModelMap model) {
        testTemplateService.copy(id);
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings", "active");
        return "redirect:/tests";
    }

    @RequestMapping(value = "/tests/{id}/delete")
    public String deleteTestTemplate(@PathVariable Long id, final ModelMap model) {
        if (testTemplateRepository.findOne(id).isEditable()) {
            testTemplateService.deleteLinkedSequences(id);
            testTemplateRepository.delete(id);
            model.clear();
        }
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings", "active");
        return "redirect:/tests";
    }
}
