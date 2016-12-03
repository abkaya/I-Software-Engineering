package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.storage.StorageService;
import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Created by Jan Huijghebaert
 */
@Controller
public class DeviceController {

    private final StorageService storageService;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    public DeviceController(StorageService storageService) {
        this.storageService = storageService;
    }

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
        Device device = deviceRepository.findOne(id);
        if(device.isUsed() || device.isInUse() || device.isDisabled()) {
            return "redirect:/devices";
        } else {
            model.addAttribute("device", deviceRepository.findOne(id));
            model.addAttribute("devicesActiveSettings", "active");
            return "devices-manage";
        }
    }

    @RequestMapping(value = {"/devices/", "/devices/{id}"}, method = RequestMethod.POST)
    public String addDevice(@Valid Device device, BindingResult result, final ModelMap model, @RequestParam("file") MultipartFile file)   {
        if(result.hasErrors())  {
            return "devices-manage";
        }
        if(!device.getDeviceName().isEmpty() && !device.getVersion().isEmpty() && !device.getType().isEmpty() && !device.getDriver().isEmpty() && !device.getManufacturer().isEmpty()) {
            boolean duplicate = false;
            for(Device devices : deviceRepository.findAll()) {
                if (device.getDeviceName().equals(devices.getDeviceName())) {
                    if (device.getVersion().equals(devices.getVersion())) {
                        duplicate = true;
                    }
                }
            }
            if(!duplicate){
                device.setImageId(device.getDeviceName(), device.getVersion());
                device.setFilesDirectoryPath(device.getImageId());
                if (!file.isEmpty()) {
                    storageService.storeImage(file, device);
                } else {
                    device.setNOImageFullPath();
                }
                deviceRepository.save(device);
            }
        }
        model.addAttribute("devicesActiveSettings","active");
        return "redirect:/devices";
    }

    @RequestMapping(value = "/devices/{id}/delete")
    public String deleteDevice(@PathVariable Long id, final ModelMap model) {
        if(deviceRepository.findOne(id).isUsed()==false) {
            storageService.deleteDevice(deviceRepository.findOne(id));
        }
        deviceService.delete(id);
        model.addAttribute("allDevices", deviceRepository.findAll());
        model.addAttribute("devicesActiveSettings","active");
        return "devices-list";
    }
}
