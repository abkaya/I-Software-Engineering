package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.TestTemplate;
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

/**
 * Created by abdil on 22/10/2016.
 */
@Controller
public class TestTemplateController {
    @Autowired
    private TestTemplateRepository testTemplateRepository;
    @Autowired
    private TestTemplateService testTemplateService;

    @RequestMapping(value="/tests", method = RequestMethod.GET)
    public String showTestTemplates(final ModelMap model){
        model.addAttribute("allTestTemplates", testTemplateRepository.findAll());
        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings","active");
        return "testTemplates-list";
    }

    @RequestMapping(value="/tests/put", method= RequestMethod.GET)
    public String viewCreateTestTemplate(final ModelMap model){
        model.addAttribute("allTestTemplates", testTemplateRepository.findAll());
        model.addAttribute("testTemplate",new TestTemplate("","",0));
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings","active");
        return "testTemplates-manage";
    }
    @RequestMapping(value="/tests/{id}", method= RequestMethod.GET)
    public String viewEditTestTemplate(@PathVariable Long id, final ModelMap model){
        //Yet to be done: load all sequences from the testSequenceRepository
        //model.addAttribute("allTestSequences", testSequenceRepository.findAll());
        model.addAttribute("testTemplate",testTemplateRepository.findOne(id));

        /*
        //used to check whether or not the partial edits caused other attributes to turn to null - now fixed
        for(int i=0;i<50;i++) {
            System.out.println(testTemplateRepository.findOne(id).getTemplateDescription());
        }*/

        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings","active");
        return "testTemplates-manage";
    }

    @RequestMapping(value={"/tests/", "/tests/{id}"}, method= RequestMethod.POST)
    public String addTestTemplate(@Valid TestTemplate testTemplate, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            //Yet to be done: load all sequences from the testSequenceRepository
            //model.addAttribute("allTestSequences", testSequenceRepository.findAll());
            return "testTemplates-manage";
        }
        testTemplateService.saveSomeAttributes(testTemplate);
        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings","active");
        return "redirect:/tests";
    }

    @RequestMapping(value="/tests/{id}/delete")
    public String deleteTestTemplate(@PathVariable Long id, final ModelMap model){
        testTemplateRepository.delete(id);
        model.clear();
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings","active");
        return "redirect:/tests";
    }
}