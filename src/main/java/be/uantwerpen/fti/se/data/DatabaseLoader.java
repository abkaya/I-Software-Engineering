package be.uantwerpen.fti.se.data;

import be.uantwerpen.fti.se.model.*;
import be.uantwerpen.fti.se.repository.*;
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
    private final DeviceRepository deviceRepository;
    private final FileRepository fileRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, DeviceRepository deviceRepository, FileRepository fileRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.fileRepository = fileRepository;
    }

    @PostConstruct
    private void initDatabase() {
        String[] allPermissions = {"user-view","user-create","user-edit","user-delete", "device-view",
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


        File f1 = new File("test.txt");
        File f2 = new File("test2.txt");
        Device d1 = new Device("keyboard");
        List<File> files = new ArrayList<>();
        f1.setPath("C:/Users/Admin/Desktop");
        f2.setPath("C:/Users/Admin/Desktop");
        files.add(f1);
        files.add(f2);
        d1.setFiles(files);
        deviceRepository.save(d1);

        Device d2 = new Device("Scherm");



    }
}
