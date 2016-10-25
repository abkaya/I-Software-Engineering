package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Testplan;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.TemplateRepository;
import be.uantwerpen.fti.se.repository.TestplanRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
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
public class TestplanController {

    @Autowired
    private TestplanRepository testplanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateRepository deviceRepository;

    @RequestMapping(value="/testplans", method= RequestMethod.GET)
    public String showTestplans(Principal principal, final ModelMap model){
        model.addAttribute("allTestplans", testplanRepository.findByUser(userRepository.findByUserName(principal.getName())));
        return "testplans-list";
    }

    @RequestMapping(value="/testplans/put", method= RequestMethod.GET)
    public String viewCreateUser(final ModelMap model){
        model.addAttribute("allTemplates", templateRepository.findAll());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("testplan",new Testplan(""));
        return "testplans-manage";
    }

    @RequestMapping(value="/testplans/{id}", method= RequestMethod.GET)
    public String viewEditUser(@PathVariable Long id, final ModelMap model){
        model.addAttribute("testplan",testplanRepository.findOne(id));
        model.addAttribute("allTemplates", templateRepository.findAll());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allDevices", deviceRepository.findAll());
        return "testplans-manage";
    }

    @RequestMapping(value={"/testplans/", "/testplans/{id}"}, method= RequestMethod.POST)
    public String addUser(@Valid Testplan testplan, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allTemplates", templateRepository.findAll());
            model.addAttribute("allUsers", userRepository.findAll());
            model.addAttribute("allDevices", deviceRepository.findAll());
            return "testplans-manage";
        }
        testplanRepository.save(testplan);
        return "redirect:/testplans";
    }

    @RequestMapping(value="/testplans/{id}/delete")
    public String deleteUser(@PathVariable Long id, final ModelMap model){
        testplanRepository.delete(id);
        model.clear();
        return "redirect:/testplans";
    }

}
