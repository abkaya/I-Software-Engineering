package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static sun.audio.AudioDevice.device;

/**
 * Created by Admin on 25-10-2016.
 */
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    public Iterable<Device> findAll() {
        return this.deviceRepository.findAll();
    }

    public void add(final Device device) {
        this.deviceRepository.save(device);
    }
    public void delete(Long id) {
        this.deviceRepository.delete(id);
    }

}
