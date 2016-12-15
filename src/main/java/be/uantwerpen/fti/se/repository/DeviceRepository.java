package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Quentin Van Ravels on 20-Oct-16.
 */
public interface DeviceRepository extends CrudRepository<Device, Long> {
    Device findByDeviceName(String deviceName);

    @Query(value = "select tp.device from TestPlan tp left join tp.users u where u=:usr")
    Iterable<Device> findDevicesByUser(@Param("usr") User user);
}