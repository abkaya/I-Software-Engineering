package be.ua.iw.ei.se.repository;

import be.ua.iw.ei.se.model.Role;
import be.ua.iw.ei.se.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Edwin on 6/10/2015.
 */

public interface RoleRepository extends CrudRepository<Role, Long> {

    List<User> findByName(String name);
}