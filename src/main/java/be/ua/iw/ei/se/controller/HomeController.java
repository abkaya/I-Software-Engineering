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

    @ModelAttribute("allUsers")
    public Iterable<User> populateUsers() {
        return this.userService.findAll();
    }

    @ModelAttribute("allRoles")
    public List<Role> populateRoles() {

        List<Role> allRoles = new ArrayList<>();
        Iterable<User> users = userService.findAll();
        for (User user : users){
            Iterable<Role> roles = user.getRoles();
            for (Role role : roles){
                if (!allRoles.contains(role)){
                    allRoles.add(role);
                }
            }
        }
        return allRoles;
    }


    @RequestMapping({"/","/home"})
    @PreAuthorize("hasRole('logon')")
    public String showHomepage(final ModelMap model){
        model.addAttribute("user", new User());
        return "homepage";
    }


    @RequestMapping(value="/users/add", method=RequestMethod.POST)
    public String add(@ModelAttribute(value="user") User user, BindingResult result, final ModelMap model) {
        if(result.hasErrors()){
            return "/users/add";
        }
        userService.add(user);
        model.clear();
        return "redirect:/home";
    }


    @RequestMapping(value="/users/{id}/delete")
    public String delete(@PathVariable Long id, ModelMap model) {
        userService.delete(id);
        return "redirect:/home";
    }


}
