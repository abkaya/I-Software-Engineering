package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
/**
 * Created by Kevin on 12/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeviceRepositoryTests {
    @Autowired
    DeviceRepository deviceRepository;
    @Test
    public void testRepositoryDevice(){
        Device device = new Device();
        device.setDeviceName("deviceX");
        assertNull(device.getId());
        deviceRepository.save(device);
        assertNotNull(device.getId());
        Device fetchdevice = deviceRepository.findOne(device.getId());
        assertNotNull(fetchdevice);
        assertEquals(device.getId(), fetchdevice.getId());
        assertEquals(device.getDeviceName(),fetchdevice.getDeviceName());
        fetchdevice.setDeviceName("deviceY");
        deviceRepository.save(fetchdevice);
        Device fetchUpdateDevice = deviceRepository.findOne(fetchdevice.getId());
        assertEquals(fetchdevice.getDeviceName(),fetchUpdateDevice.getDeviceName());
        long deviceCount = deviceRepository.count();
        assertEquals(deviceCount,5);
        Iterable<Device> devices = deviceRepository.findAll();
        int count = 0;
        for(Device d : devices){
            count++;
        }
        assertEquals(count, 5);
    }
}
