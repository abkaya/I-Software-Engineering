package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quentin Van Ravels on 20-Oct-16.
 */
@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    public Iterable<Device> findAll() {return this.deviceRepository.findAll();}
    public void add(final Device device){ this.deviceRepository.save(device);}
    public void delete(Long id) {
        Device device = this.deviceRepository.findOne(id);
        if (device.isUsed()) {
            device.setDisabled(true);
        } else {
            this.deviceRepository.delete(id);
        }
    }

    public void saveSomeAttributes(Device device) {
        Device tempDevice = device.getId() == null ? null : deviceRepository.findOne(device.getId());



        if (tempDevice != null) {
            tempDevice.setDeviceName(device.getDeviceName());
            tempDevice.setDeviceClass(device.getDeviceClass());
            tempDevice.setType(device.getType());
            tempDevice.setDriver(device.getDriver());
            tempDevice.setManufacturer(device.getManufacturer());
            tempDevice.setUsed(device.isUsed());
            tempDevice.setDisabled(device.isDisabled());
            deviceRepository.save(tempDevice);
        }else{
            deviceRepository.save(device);
        }
    }

    public Device findByDeviceName(String deviceName){return deviceRepository.findByDeviceName(deviceName);}
}
