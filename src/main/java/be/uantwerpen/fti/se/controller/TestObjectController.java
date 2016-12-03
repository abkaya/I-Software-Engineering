package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.TestObjectRepository;
import be.uantwerpen.fti.se.service.TestObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Quentin Van Ravels on 26-Nov-16.
 */
@Controller
public class TestObjectController {

    @Autowired
    private TestObjectRepository testObjectRepository;
    @Autowired
    private TestObjectService testObjectService;

    @RequestMapping(value = "/testobjects", method = RequestMethod.GET)
    public String showTestObject(Principal principal, final ModelMap model){
        model.addAttribute("myTestObjects", testObjectService.findForUser(principal.getName()));
        model.addAttribute("testObjectsActiveSettings", "active");
        return "testobject-list";
    }

    @RequestMapping(value = "/testobjects/{id}", method = RequestMethod.GET)
    public String takeTest(@PathVariable Long id, Principal principal, final ModelMap model){
        //this function should start the test. As a placeholder dud results are created
        testObjectService.finishTest(testObjectRepository.findOne(id));
        model.addAttribute("myTestObjects", testObjectService.findForUser(principal.getName()));
        model.addAttribute("testObjectsActiveSettings", "active");
        return "testobject-list";
    }

}
