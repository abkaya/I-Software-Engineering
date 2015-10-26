package be.ua.iw.ei.se.repository;

import be.ua.iw.ei.se.model.Permission;
import be.ua.iw.ei.se.model.Role;
import be.ua.iw.ei.se.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 6/10/2015.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String userName);
}