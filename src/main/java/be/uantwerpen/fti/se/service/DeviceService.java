package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Quentin Van Ravels on 20-Oct-16.
 */
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

    public Device findByDeviceName(String deviceName){return deviceRepository.findByDeviceName(deviceName);}
}
