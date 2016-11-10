package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Created by Jan Huijghebaert on 20-Oct-16.
 */
@Controller
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public String showDevices(final ModelMap model) {
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("devicesActiveSettings","active");
        return "devices-list";
    }

    @RequestMapping(value = "/devices/put", method = RequestMethod.GET)
    public String viewCreateDevice(final ModelMap model)    {
        model.addAttribute("device", new Device());
        model.addAttribute("devicesActiveSettings","active");
        return "devices-manage";
    }

    @RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
    public String viewEditDevice(@PathVariable Long id, final ModelMap model)  {
        model.addAttribute("device",deviceRepository.findOne(id));
        model.addAttribute("devicesActiveSettings","active");
        return "devices-manage";
    }

    @RequestMapping(value = {"/devices/", "/devices/{id}"}, method = RequestMethod.POST)
    public String addDevice(@Valid Device device, BindingResult result, @RequestParam("file") MultipartFile file, final ModelMap model)   {
        if(result.hasErrors())  {
            return "devices-manage";
        }
        if (!file.isEmpty()) {
            return "/home";
        }
        deviceRepository.save(device);
        model.addAttribute("devicesActiveSettings","active");
        return "redirect:/devices";
    }

    @RequestMapping(value = "/devices/{id}/delete")
    public String deleteDevice(@PathVariable Long id, final ModelMap model) {
        deviceService.delete(id);
        model.clear();
        model.addAttribute("devicesActiveSettings","active");
        return "redirect:/devices";
    }

    /*
    @RequestMapping(value = "/devices/{id}/files", method = RequestMethod.GET)
    public String viewFiles(@PathVariable Long id, final ModelMap model)  {
        model.addAttribute("device",deviceRepository.findOne(id));
        model.addAttribute("devicesActiveSettings","active");
        return "load-file";
    }
    */
}
