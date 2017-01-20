package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.TestObject;
import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Quentin Van Ravels on 24-Nov-16.
 */
public interface TestObjectRepository extends CrudRepository<TestObject, Long> {

    @Query(value = "select to.testPlan.device.deviceName from TestObject to group by to.testPlan.device.deviceName")
    Iterable<String> getDeviceNames();

    @Query(value = "select to from TestObject to where to.user=:usr")
    Iterable<TestObject> findForUser(@Param("usr") String user);


}