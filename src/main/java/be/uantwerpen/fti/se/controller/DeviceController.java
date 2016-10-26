package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.File;
import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.repository.FileRepository;
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
    private DeviceRepository deviceRepository;
    @Autowired
    private FileRepository fileRepository;

    @RequestMapping(value="/devices", method= RequestMethod.GET)
    public String showDevices(final ModelMap model){
        model.addAttribute("allDevices", deviceRepository.findAll());
        return "device-list";
    }

    @RequestMapping(value="/devices/put", method= RequestMethod.GET)
    public String viewCreateFile(final ModelMap model){
        model.addAttribute("allFiles", fileRepository.findAll());
        model.addAttribute("file",new File(""));
        return "device-manage";
    }

    @RequestMapping(value="/devices/{id}", method= RequestMethod.GET)
    public String viewEditFile(@PathVariable Long id, final ModelMap model){
        model.addAttribute("allFiles", fileRepository.findAll());
        model.addAttribute("device",deviceRepository.findOne(id));
        return "device-select";
    }

    @RequestMapping(value={"/devices/", "/devices/{id}"}, method= RequestMethod.POST)
    public String addFile(@Valid File file, BindingResult result, final ModelMap model){
        if(result.hasErrors()){
            model.addAttribute("allFiles", fileRepository.findAll());
            return "device-manage";
        }
        fileRepository.save(file);
        return "redirect:/devices";
    }
}

