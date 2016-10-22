package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.repository.PermissionRepository;
import be.uantwerpen.fti.se.repository.RoleRepository;
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
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @RequestMapping(value="/roles", method= RequestMethod.GET)
    public String showRoles(final ModelMap model){
        model.addAttribute("allRoles", roleRepository.findAll());
        //Set the navigation button Role Management to active
        model.addAttribute("rolesActiveSettings","active");
        return "roles-list";
    }
    @RequestMapping(value="/roles/put", method= RequestMethod.GET)
    public String viewCreateRole(final ModelMap model){
        model.addAttribute("allPermissions", permissionRepository.findAll());
        model.addAttribute("role",new Role(""));
        //Set the navigation button Role Management to active
        model.addAttribute("rolesActiveSettings","active");
        return "roles-manage";
    }
    @RequestMapping(value="/roles/{id}", method= RequestMethod.GET)
    public String viewEditRole(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allPermissions", permissionRepository.findAll());
        model.addAttribute("role",roleRepository.findOne(id));
        //Set the navigation button Role Management to active
        model.addAttribute("rolesActiveSettings","active");
        return "roles-manage";
    }

    @RequestMapping(value={"/roles/", "/roles/{id}"}, method= RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allPermissions", permissionRepository.findAll());
            return "roles-manage";
        }
        roleRepository.save(role);
        //Set the navigation button Role Management to active
        model.addAttribute("rolesActiveSettings","active");
        return "redirect:/roles";
    }


    @RequestMapping(value="/roles/{id}/delete")
    public String deleteRole(@PathVariable Long id, final ModelMap model){
        roleRepository.delete(id);
        model.clear();
        //Set the navigation button Role Management to active
        model.addAttribute("rolesActiveSettings","active");
        return "redirect:/roles";
    }


}
