package be.uantwerpen.fti.se.data;

import be.uantwerpen.fti.se.model.*;
import be.uantwerpen.fti.se.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
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
    private final TestTemplateRepository testTemplateRepository;
    private final TestSequenceRepository testSequenceRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, TestTemplateRepository testTemplateRepository, TestSequenceRepository testSequenceRepository, DeviceRepository deviceRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.testTemplateRepository = testTemplateRepository;
        this.testSequenceRepository = testSequenceRepository;
        this.deviceRepository = deviceRepository;
    }

    @PostConstruct
    private void initDatabase() {
        //Array of permissions, to be saved in p and later to be assigned to the administrator role.
        String[] allPermissions = {"user-view", "user-create", "user-edit", "user-delete",
                "role-view", "role-create", "role-edit", "role-delete", "test-view", "test-create", "test-edit", "test-delete", "device-view", "device-create", "device-edit", "devioe-delete"};
        for (String p : allPermissions) {
            permissionRepository.save(new Permission(p));
        }

        //create logon permission and save it to the repository
        Permission p1 = new Permission("logon");
        permissionRepository.save(p1);

        //create admin and tester roles
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");

        //add permission "logon" to the newly created list permissions.
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(p1);

        //now add all permissions in permissions to the tester role (currently just 1)
        tester.setPermissions(permissions);
        roleRepository.save(tester);

        //now add all permissions from the String array above (allPermissions) to the newly created permissions.
        permissions = new ArrayList<Permission>();
        for (Permission p : permissionRepository.findAll()) {
            permissions.add(p);
        }

        //add all these permissions to the administrator role
        administrator.setPermissions(permissions);
        roleRepository.save(administrator);

        //create users and set roles
        User u1 = new User("admin", "admin");
        List<Role> roles = new ArrayList<>();
        roles.add(administrator);
        u1.setRoles(roles);
        userRepository.save(u1);
        User u2 = new User("user", "user");
        roles = new ArrayList<>();
        roles.add(tester);
        u2.setRoles(roles);
        userRepository.save(u2);

        //create sequences and add these to the repository
        TestSequence ts0 = new TestSequence();
        TestSequence ts1 = new TestSequence(3, 10, 1, 3);
        TestSequence ts2 = new TestSequence();
        TestSequence ts3 = new TestSequence();
        TestSequence ts4 = new TestSequence();
        testSequenceRepository.save(ts0);
        testSequenceRepository.save(ts1);
        testSequenceRepository.save(ts2);
        testSequenceRepository.save(ts3);
        testSequenceRepository.save(ts4);

        //test creating a template and adding it to the repo
        TestTemplate t1 = new TestTemplate("Template_0x01", "Brief description of the template.", true, 5, 30, 1, 5, 30,50);
        TestTemplate t2 = new TestTemplate("Template_0x02", "Brief description of the template.", true, 10, 30, 1, 5, 30,50);
        TestTemplate t3 = new TestTemplate("Template_0x03", "Brief description of the template.", true, 20, 30, 1, 5, 30,50);
        TestTemplate t4 = new TestTemplate("Template_0x04", "Brief description of the template.", true, 45, 30, 1, 5, 30,50);
        TestTemplate t5 = new TestTemplate("Template_0x05_editable_set_FALSE", "THIS TEMPLATE IS _NOT_ EDITABLE. Copying is possible.",false, 65, 30, 1, 5, 30,50);
        TestTemplate t6 = new TestTemplate("Template_0x06", "Brief description of the template.", true, 85, 30, 1, 5, 30,50);
        TestTemplate t7 = new TestTemplate("Template_0x07", "Brief description of the template.", true, 105 , 30, 1, 5, 30,50);
        TestTemplate t8 = new TestTemplate("Template_0x08_editable_set_FALSE", "THIS TEMPLATE IS _NOT_ EDITABLE. Copying is possible.", false, 135, 30, 1, 5, 30,50);

        testTemplateService.saveSomeAttributes(t1);
        testTemplateService.saveSomeAttributes(t2);
        testTemplateService.saveSomeAttributes(t3);
        testTemplateService.saveSomeAttributes(t4);
        testTemplateService.saveSomeAttributes(t5);
        testTemplateService.saveSomeAttributes(t6);
        testTemplateService.saveSomeAttributes(t7);
        testTemplateService.saveSomeAttributes(t8);


        //Add devices
        Device d1 = new Device("aDevice", "aType", "aVersion", "aManufacturer", "aDriver", true);
        Device d2 = new Device("aDevice2", "aType2", "aVersion2", "aManufacturer2", "aDriver2", false);
        Device d3 = new Device("aDevice3", "aType3", "aVersion3", "aManufacturer3", "aDriver3", true);
        Device d4 = new Device("aDevice4", "aType4", "aVersion4", "aManufacturer4", "aDriver4", true);
        d1.setIsUsed();
        d2.setDisabled();
        deviceRepository.save(d1);
        deviceRepository.save(d2);


    }
}