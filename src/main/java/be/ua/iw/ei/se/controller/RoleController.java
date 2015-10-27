package be.ua.iw.ei.se.controller;

import be.ua.iw.ei.se.model.Role;
import be.ua.iw.ei.se.repository.PermissionRepository;
import be.ua.iw.ei.se.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Edwin on 27/10/2015.
 */
@Controller
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @RequestMapping({"/roles"})
    public String showRoles(final ModelMap model){
        model.addAttribute("allRoles", roleRepository.findAll());
        return "roles-list";
    }
    @RequestMapping({"/roles/manage"})
    public String manageRoles(final ModelMap model){
        model.addAttribute("allPermissions", permissionRepository.findAll());
        model.addAttribute("role",new Role(""));
        return "roles-manage";
    }
    @RequestMapping({"/roles/{id}"})
    public String manageRole(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allPermissions", permissionRepository.findAll());
        model.addAttribute("role",roleRepository.findOne(id));
        return "roles-manage";
    }


}
