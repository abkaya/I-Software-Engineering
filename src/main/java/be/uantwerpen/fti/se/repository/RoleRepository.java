package be.uantwerpen.fti.se.repository;

import be.uantwerpen.fti.se.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Edwin on 6/10/2015.
 */

public interface RoleRepository extends CrudRepository<Role, Long> {
}