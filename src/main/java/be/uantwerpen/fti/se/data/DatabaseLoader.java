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
    private final TestTemplateRepository testTemplateRepository;
    private final TestSequenceRepository testSequenceRepository;
    private final DeviceRepository deviceRepository;
    private final TestPlanRepository testPlanRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, TestTemplateRepository testTemplateRepository, TestSequenceRepository testSequenceRepository, DeviceRepository deviceRepository, TestPlanRepository testPlanRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.testTemplateRepository = testTemplateRepository;
        this.testSequenceRepository = testSequenceRepository;
        this.deviceRepository = deviceRepository;
        this.testPlanRepository = testPlanRepository;
    }

    @PostConstruct
    private void initDatabase() {
        //Array of permissions, to be saved in p and later to be assigned to the administrator role.
        String[] allPermissions = {"user-view", "user-create", "user-edit", "user-delete",
                "role-view", "role-create", "role-edit", "role-delete", "test-view", "test-create", "test-edit", "test-delete", "device-view", "device-create", "device-edit", "device-delete",  "testplan-view", "testplan-create", "testplan-edit", "testplan-delete",};
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

        //Add devices
        Device d1 = new Device("aDevice", "aType", "aVersion", "aManufacturer", "aDriver");
        Device d2 = new Device("aDevice2", "aType2", "aVersion2", "aManufacturer2", "aDriver2");
        Device d3 = new Device("aDevice3", "aType3", "aVersion3", "aManufacturer3", "aDriver3");
        Device d4 = new Device("aDevice4", "aType4", "aVersion4", "aManufacturer4", "aDriver4");
        d1.setIsUsed();
        d2.setIsUsed();
        d2.setDisabled();
        d3.setIsInUse();
        deviceRepository.save(d1);
        deviceRepository.save(d2);
        deviceRepository.save(d3);
        deviceRepository.save(d4);



        //Add test plans
        TestPlan tp1 = new TestPlan("Testplan_0x01","08/08","09/08","This is a description for testplan 1");
        TestPlan tp2 = new TestPlan("Testplan_0x02","08/08","09/08","This is a description for testplan 2");

        t1.setEditable(false);
        tp1.setTestTemplate(t1);
        List<User> testPlanUsers = new ArrayList<User>();
        testPlanUsers.add(u1);
        tp1.setUsers(testPlanUsers);
        List<Device> testPlanDevices = new ArrayList<Device>();
        d1.setIsInUse();
        d1.setIsUsed();
        testPlanDevices.add(d1);
        d1.setIsInUse();
        tp1.setDevices(testPlanDevices);
        testPlanRepository.save(tp1);

        t2.setEditable(false);
        tp2.setTestTemplate(t2);
        testPlanUsers.clear();
        testPlanUsers.add(u2);
        tp2.setUsers(testPlanUsers);
        testPlanDevices.clear();
        d2.setIsInUse();
        d2.setIsUsed();
        testPlanDevices.add(d2);
        tp2.setDevices(testPlanDevices);
        testPlanRepository.save(tp2);





    }
}