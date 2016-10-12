package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Edwin on 6/10/2015.
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    public Iterable<Permission> findAllForUser(User user){
        return permissionRepository.findAllForUser(user);
    }
}
