
package be.uantwerpen.fti.se.controller;


import be.uantwerpen.fti.se.model.TestPlan;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.repository.TestPlanRepository;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import be.uantwerpen.fti.se.service.TestPlanService;
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
 * Created by Willem on 24/10/2016.
 */

@Controller
public class TestPlanController {

    @Autowired
    private TestPlanService testPlanService;
    @Autowired
    private TestPlanRepository testPlanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestTemplateRepository testTemplateRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping(value="/testplans", method= RequestMethod.GET)
    public String showTestplans(Principal principal, final ModelMap model){
        System.out.printf("\n The logged user is: " + principal.getName() + "\n" );

        if(userRepository.findByUserName(principal.getName()).isAdmin()) {
            model.addAttribute("allTestplans", testPlanService.findByUserName(userRepository.findByUserName(principal.getName())));
        /*
        model.addAttribute("allTestplans", testPlanRepository.findAll());
        */
            return "testplans-list";
        }
        else{
            return "redirect:/";
        }
    }

    @RequestMapping(value="/testplans/put", method= RequestMethod.GET)
    public String viewCreateTestPlan(Principal principal, final ModelMap model){
        if(userRepository.findByUserName(principal.getName()).isAdmin()) {
            model.addAttribute("allTemplates", testTemplateRepository.findAll());
            model.addAttribute("allUsers", userRepository.findAll());
            model.addAttribute("allDevices", deviceRepository.findAll());
            model.addAttribute("testPlan", new TestPlan());
            return "testplans-manage";
        }
        else{
            return "redirect:/";
        }
    }

    @RequestMapping(value="/testplans/{id}", method= RequestMethod.GET)
    public String viewEditTestPlan(Principal principal, @PathVariable Long id, final ModelMap model){
        if(userRepository.findByUserName(principal.getName()).isAdmin()) {
            model.addAttribute("testPlan", testPlanRepository.findOne(id));
            model.addAttribute("allTemplates", testTemplateRepository.findAll());
            model.addAttribute("testTemplate", testPlanRepository.findOne(id).getTestTemplate());
            model.addAttribute("allUsers", userRepository.findAll());
            model.addAttribute("allDevices", deviceRepository.findAll());
            return "testplans-manage";
        }
        else{
            return "redirect:/";
        }
    }

    @RequestMapping(value={"/testplans/", "/testplans/{id}"}, method= RequestMethod.POST)
    public String addTestPlan(Principal principal, @Valid TestPlan testplan, BindingResult result, final ModelMap model){
        if(userRepository.findByUserName(principal.getName()).isAdmin()) {
            if (result.hasErrors()) {
                model.addAttribute("allTemplates", testTemplateRepository.findAll());
                model.addAttribute("allUsers", userRepository.findAll());
                model.addAttribute("allDevices", deviceRepository.findAll());
                return "testplans-manage";
            }
            if(testplan.isCompleted()) {
                return "redirect:/questionssurvey";
            }
            if(testplan.checkTestplan()) {
                testPlanService.saveSomeAttributes(testplan);
                testPlanService.createTestObject(testplan);
                return "redirect:/testplans";
            }
            return "redirect:/testplans";

        }
        return "redirect:/";
    }

    @RequestMapping(value="/testplans/{id}/delete")
    public String deleteTestPlan(Principal principal, @PathVariable Long id, final ModelMap model){
        if(userRepository.findByUserName(principal.getName()).isAdmin()) {
            testPlanService.delete(id);
            model.clear();
            return "redirect:/testplans";
        }
        return "redirect:/";

    }

}
