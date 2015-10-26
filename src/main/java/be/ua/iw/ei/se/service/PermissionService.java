package be.ua.iw.ei.se.service;

import be.ua.iw.ei.se.model.Permission;
import be.ua.iw.ei.se.model.User;
import be.ua.iw.ei.se.repository.PermissionRepository;
import be.ua.iw.ei.se.repository.UserRepository;
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
