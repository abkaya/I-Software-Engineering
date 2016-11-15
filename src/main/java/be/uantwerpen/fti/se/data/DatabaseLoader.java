package be.uantwerpen.fti.se.data;

import be.uantwerpen.fti.se.model.*;
import be.uantwerpen.fti.se.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
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
        TestTemplate t1 = new TestTemplate("Template_0x01", "Brief description of the template.");
        TestTemplate t2 = new TestTemplate("Template_0x02", "Brief description of the template.");
        TestTemplate t3 = new TestTemplate("Template_0x03", "Brief description of the template.");
        TestTemplate t4 = new TestTemplate("Template_0x04", "Brief description of the template.");
        TestTemplate t5 = new TestTemplate("Template_0x05", "Brief description of the template.");
        TestTemplate t6 = new TestTemplate("Template_0x06", "Brief description of the template.");
        TestTemplate t7 = new TestTemplate("Template_0x07", "Brief description of the template.");
        TestTemplate t8 = new TestTemplate("Template_0x08_editable_set_FALSE", "THIS TEMPLATE IS _NOT_ EDITABLE. CHANGES WILL NOT BE SAVED");
        t8.setEditable(false);

        List<TestSequence> testSequences = new ArrayList<TestSequence>();
        testSequences.add(ts1);

        t2.setTestSequences(testSequences);
        testTemplateRepository.save(t2);

        //add add all sequences to t1
        testSequences = new ArrayList<TestSequence>();
        for (TestSequence ts : testSequenceRepository.findAll()) {
            testSequences.add(ts);
        }
        t1.setTestSequences(testSequences);
        testTemplateRepository.save(t1);

        testTemplateRepository.save(t3);
        testTemplateRepository.save(t4);
        testTemplateRepository.save(t5);
        testTemplateRepository.save(t6);
        testTemplateRepository.save(t7);
        testTemplateRepository.save(t6);
        testTemplateRepository.save(t8);

        //voeg devices toe
        Device d1 = new Device("aDevice", "aType", "aClass", "aManufacturer", "aDriver");
        Device d2 = new Device("bDevice2", "bType2", "bClass2", "bManufacturer2", "bDriver2");
        d1.setIsUsed();
        d2.setDisabled();
        deviceRepository.save(d1);
        deviceRepository.save(d2);

    }
}