package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Quentin Van Ravels on 20-Oct-16.
 * Edited by Jan Huijghebaert on 25-Oct-16
 */
@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    public Iterable<Device> findAll() {return this.deviceRepository.findAll();}
    public void add(final Device device){ this.deviceRepository.save(device);}
    public void delete(Long id) {
        Device device = this.deviceRepository.findOne(id);
        if (device.isUsed() && !device.isInUse()) {
            this.deviceRepository.findOne(id).setDisabled();
        } else if(device.isInUse()) {//warn user of error
        }else{
            this.deviceRepository.delete(id);
        }
    }

    public void saveSomeAttributes(Device device) {
        Device tempDevice = device.getId() == null ? null : deviceRepository.findOne(device.getId());
        if (tempDevice != null) {
            tempDevice.setDeviceName(device.getDeviceName());
            tempDevice.setVersion(device.getVersion());
            tempDevice.setType(device.getType());
            tempDevice.setDriver(device.getDriver());
            tempDevice.setVersion(device.getVersion());
            if(device.isUsed()) {
                tempDevice.setIsUsed();
            } else {
                tempDevice.setIsUnused();
            }
            if(device.isDisabled()) {
                tempDevice.setDisabled();
            } else {
                tempDevice.setEnabled();
            }
            deviceRepository.save(tempDevice);
        }else{
            deviceRepository.save(device);
        }
    }

    public Device findByDeviceName(String deviceName){return deviceRepository.findByDeviceName(deviceName);}
}
