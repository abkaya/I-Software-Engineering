package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.DeviceRepository;
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
    private UserService userService;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping({"/","/home"})
    @PreAuthorize("hasAuthority('logon')")
    public String showHomepage(final ModelMap model){
        model.addAttribute("user", new User());

        int size = 0;
        for(User users : userRepository.findAll()){
            size = size + 1;
        }
        model.addAttribute("noUsers", size);

        size = 0;
        for (Device devices : deviceRepository.findAll()) {
            size = size + 1;
        }
        model.addAttribute("noDevices", size);

        //Set the navigation button Home Management to active
        model.addAttribute("homeActiveSettings","active");
        return "homepage";
    }
}
