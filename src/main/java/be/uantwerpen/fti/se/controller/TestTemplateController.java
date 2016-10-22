package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by abdil on 22/10/2016.
 */
@Controller
public class TestTemplateController {
    @Autowired
    private TestTemplateRepository testTemplateRepository;

    @RequestMapping(value="/tests", method = RequestMethod.GET)
    public String showTestTemplates(final ModelMap model){
        model.addAttribute("allTestTemplates", testTemplateRepository.findAll());
        return "testTemplates-list";
    }
}
