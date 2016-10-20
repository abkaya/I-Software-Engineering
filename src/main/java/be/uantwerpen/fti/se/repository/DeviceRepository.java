package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Quentin Van Ravels on 20-Oct-16.
 */
public interface DeviceRepository extends CrudRepository<Device, Long> {
}