package be.uantwerpen.fti.se.data;

import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.PermissionRepository;
import be.uantwerpen.fti.se.repository.RoleRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 21/10/2015.
 */
@Service
@Profile("default")
public class DatabaseLoader {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initDatabase() {
        String[] allPermissions = {"user-view","user-create","user-edit","user-delete",
                "role-view","role-create","role-edit","role-delete"};
        for (String p : allPermissions){
            permissionRepository.save(new Permission(p));
        }
        Permission p1 = new Permission("logon");
        permissionRepository.save(p1);
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");
        List<Permission> permissions =  new ArrayList<Permission>();
        permissions.add(p1);
        tester.setPermissions(permissions);
        roleRepository.save(tester);
        permissions =  new ArrayList<Permission>();
        for (Permission p : permissionRepository.findAll()){
            permissions.add(p);
        }
        administrator.setPermissions(permissions);
        roleRepository.save(administrator);
        User u1 = new User("admin","admin");
        List<Role> roles = new ArrayList<>();
        roles.add(administrator);
        u1.setRoles(roles);
        userRepository.save(u1);
        User u2 = new User("user","user");
        roles = new ArrayList<>();
        roles.add(tester);
        u2.setRoles(roles);
        userRepository.save(u2);

    }
}
