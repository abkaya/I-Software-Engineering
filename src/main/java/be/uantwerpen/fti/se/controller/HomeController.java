package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Edwin on 29/09/2015.
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @RequestMapping({"/","/home"})
    @PreAuthorize("hasAuthority('logon')")
    public String showHomepage(final ModelMap model){
        model.addAttribute("user", new User());
        //Set the navigation button Home Management to active
        model.addAttribute("homeActiveSettings","active");
        return "homepage";
    }


}
