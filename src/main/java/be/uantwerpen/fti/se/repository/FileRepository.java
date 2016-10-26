package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Device;
import be.uantwerpen.fti.se.model.File;
import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Admin on 25-10-2016.
 */
public interface FileRepository extends CrudRepository<File, Long> {

    /*
    @Query(value = "select p from User u left join u.roles r left join r.permissions  p where u=:dev")
    Iterable<File> findAllForDevice(@Param("dev") Device device);
    */
}
