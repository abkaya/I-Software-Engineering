package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Admin on 24-10-2016.
 */
public interface DeviceRepository extends CrudRepository<Device, Long> {

}