package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Edwin on 6/10/2015.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String userName);
}