package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.repository.PermissionRepository;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Admin on 24-10-2016.
 */

@Controller
public class DeviceController {
    @Autowired
    private DeviceRepository DeviceRepository;

    @RequestMapping(value="/devices", method= RequestMethod.GET)
    public String showDevices(final ModelMap model){
        model.addAttribute("device",new Device(""));
        return "device-manage";
    }

}

