package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Edwin on 6/10/2015.
 */

public interface PermissionRepository extends CrudRepository<Permission, Long> {

    @Query(value = "select p from User u left join u.roles r left join r.permissions  p where u=:usr")
    Iterable<Permission> findAllForUser(@Param("usr") User user);
}