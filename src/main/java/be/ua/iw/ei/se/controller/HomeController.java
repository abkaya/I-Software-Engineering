package be.ua.iw.ei.se.controller;

import be.ua.iw.ei.se.model.Role;
import be.ua.iw.ei.se.model.User;
import be.ua.iw.ei.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by Edwin on 29/09/2015.
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @RequestMapping({"/","/home"})
    @PreAuthorize("hasRole('logon')")
    public String showHomepage(final ModelMap model){
        model.addAttribute("user", new User());
        return "homepage";
    }


}
