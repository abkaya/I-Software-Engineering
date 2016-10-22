package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.RoleRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Edwin on 27/10/2015.
 */
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value="/users", method= RequestMethod.GET)
    public String showUsers(final ModelMap model){
        model.addAttribute("allUsers", userRepository.findAll());
        //Set the navigation button User Management to active
        model.addAttribute("usersActiveSettings","active");
        return "users-list";
    }
    @RequestMapping(value="/users/put", method= RequestMethod.GET)
    public String viewCreateUser(final ModelMap model){
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("user",new User("",""));
        //Set the navigation button User Management to active
        model.addAttribute("usersActiveSettings","active");
        return "users-manage";
    }
    @RequestMapping(value="/users/{id}", method= RequestMethod.GET)
    public String viewEditUser(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("user",userRepository.findOne(id));
        //Set the navigation button User Management to active
        model.addAttribute("usersActiveSettings","active");
        return "users-manage";
    }

    @RequestMapping(value={"/users/", "/users/{id}"}, method= RequestMethod.POST)
    public String addUser(@Valid User user, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allRoles", roleRepository.findAll());
            //Set the navigation button User Management to active
            model.addAttribute("usersActiveSettings","active");
            return "users-manage";
        }
        userRepository.save(user);
        return "redirect:/users";
    }


    @RequestMapping(value="/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, final ModelMap model){
        userRepository.delete(id);
        model.clear();
        //Set the navigation button User Management to active
        model.addAttribute("usersActiveSettings","active");
        return "redirect:/users";
    }


}
