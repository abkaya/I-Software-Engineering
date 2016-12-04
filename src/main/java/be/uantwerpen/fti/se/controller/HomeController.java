package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.repository.TestPlanRepository;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import be.uantwerpen.fti.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Edwin on 29/09/2015.
 */
@Controller
public class HomeController {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestPlanRepository testPlanRepository;
    @Autowired
    private TestTemplateRepository testTemplateRepository;
    @Autowired
    private TestTemplateRepository testObjectRepository;

    @RequestMapping({"/","/home"})
    @PreAuthorize("hasAuthority('logon')")
    public String showHomepage(final ModelMap model){

        model.addAttribute("amountUsers", userRepository.count());
        model.addAttribute("amountDevices", deviceRepository.count());
        model.addAttribute("amountTestPlans", testPlanRepository.count());
        model.addAttribute("amountTestTemplates", testTemplateRepository.count());
        model.addAttribute("allTestObjects", testObjectRepository.findAll());

        //Set the navigation button Home Management to active
        model.addAttribute("homeActiveSettings","active");
        return "homepage";
    }

}
