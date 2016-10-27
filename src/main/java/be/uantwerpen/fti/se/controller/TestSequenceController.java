package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.service.TestSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Kevin on 23/10/2016.
 */
@Controller
public class TestSequenceController {
    @Autowired
    private TestSequenceRepository testSequenceRepository;
    @Autowired
    private TestSequenceService testSequenceService;

    @RequestMapping(value="/testSequences", method = RequestMethod.GET)
    public String showTestSequences(final ModelMap model){
        model.addAttribute("allTestSequences", testSequenceRepository.findAll());
        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings","active");
        return "testSequences-list";
    }

    @RequestMapping(value="/testSequences/put", method= RequestMethod.GET)
    public String viewCreateTestSequences(final ModelMap model){
        model.addAttribute("allTestSequences", testSequenceRepository.findAll());
        model.addAttribute("testSequence",new TestSequence(1,11));
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings","active");
        return "testSequences-manage";
    }

    @RequestMapping(value="/testSequence/{id}", method= RequestMethod.GET)
    public String viewEditTestSequence(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allTestSequences", testSequenceRepository.findAll());
        model.addAttribute("testSequence",testSequenceRepository.findOne(id));

        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings","active");
        return "testSequences-manage";
    }

   @RequestMapping(value={"/testSequences/", "/testSequences/{id}"}, method= RequestMethod.POST)
    public String addTestSequence(@Valid TestSequence testSequence, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allTestSequences", testSequenceRepository.findAll());
            return "testSequences-manage";
        }
        testSequenceService.saveSomeAttributes(testSequence);
        //Set the navigation button Test Management to active
        model.addAttribute("testsActiveSettings","active");
        return "redirect:/testSequences";
    }

    @RequestMapping(value="/testSequence/{id}/delete")
    public String deleteTestSequence(@PathVariable Long id, final ModelMap model){
        testSequenceRepository.delete(id);
        model.clear();
        //Set the navigation button Tests Management to active
        model.addAttribute("testsActiveSettings","active");
        return "redirect:/testSequences";
    }
}
