package be.uantwerpen.fti.se.data;

import be.uantwerpen.fti.se.model.*;
import be.uantwerpen.fti.se.repository.*;
import be.uantwerpen.fti.se.service.TestTemplateService;
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
    private final TestTemplateService testTemplateService;
    private final TestSequenceRepository testSequenceRepository;
    private final DeviceRepository deviceRepository;
    private final TestPlanRepository testPlanRepository;
    private final TestObjectRepository testObjectRepository;
    private final ResultRepository resultRepository;
    private final SurveyRepository surveyRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, TestTemplateRepository testTemplateRepository, TestTemplateService testTemplateService, TestSequenceRepository testSequenceRepository, DeviceRepository deviceRepository, TestPlanRepository testPlanRepository, TestObjectRepository testObjectRepository, ResultRepository resultRepository, SurveyRepository surveyRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.testTemplateRepository = testTemplateRepository;
        this.testTemplateService = testTemplateService;
        this.testSequenceRepository = testSequenceRepository;
        this.deviceRepository = deviceRepository;
        this.testPlanRepository = testPlanRepository;
        this.testObjectRepository = testObjectRepository;
        this.resultRepository = resultRepository;
        this.surveyRepository = surveyRepository;

    }

    @PostConstruct
    private void initDatabase() {
        //Array of permissions, to be saved in p and later to be assigned to the administrator role.
        String[] adminPermissions = {"logon", "user-view", "user-create", "user-edit", "user-delete", "survey-view",
                "role-view", "role-create", "role-edit", "role-delete", "device-view", "device-create", "device-edit", "device-delete", "test-view", "test-create", "test-edit", "test-delete", "testplan-view", "testplan-create", "testplan-edit", "testplan-delete"};
        String[] userPermissions = {"logon", "surveyQ-view", "device-view", "device-create", "device-edit", "device-delete", "mytests"};
        for (String p : userPermissions) {
            permissionRepository.save(new Permission(p));
        }

        //create logon permission and save it to the repository
        //Permission p1 = new Permission("logon");
        //permissionRepository.save(p1);
        //Permission p2 = new Permission("device-view");
        //permissionRepository.save(p2);

        //create admin and tester roles
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");

        //add permission "logon" to the newly created list permissions.
        List<Permission> permissionsUser = new ArrayList<Permission>();
        for (Permission p : permissionRepository.findAll()) {
            permissionsUser.add(p);
        }
        //permissions.add(p1);
        //permissions.add(p2);

        //now add all permissions in permissions to the tester role (currently just 1)
        tester.setPermissions(permissionsUser);
        roleRepository.save(tester);

        //now add all permissions from the String array above (allPermissions) to the newly created permissions.
        List<Permission> permissionsAdmin = new ArrayList<Permission>();
        for (String p : adminPermissions) {
            permissionRepository.save(new Permission(p));
        }
        //for (Permission p : permissionRepository2.findAll()) {
        //    permissionsAdmin.add(p);
        //}
        for (long i=8;i<=29;i++){
            Permission p = permissionRepository.findOne(i);
            permissionsAdmin.add(p);
        }

        //add all these permissions to the administrator role
        administrator.setPermissions(permissionsAdmin);
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
        User u3 = new User("user2", "user2");
        roles = new ArrayList<>();
        roles.add(tester);
        u3.setRoles(roles);
        userRepository.save(u3);

        //create sequences and add these to the repository
        /*TestSequence ts0 = new TestSequence();
        TestSequence ts1 = new TestSequence(3, 10, 1, 3);
        TestSequence ts2 = new TestSequence();
        TestSequence ts3 = new TestSequence();
        TestSequence ts4 = new TestSequence();
        testSequenceRepository.save(ts0);
        testSequenceRepository.save(ts1);
        testSequenceRepository.save(ts2);
        testSequenceRepository.save(ts3);
        testSequenceRepository.save(ts4);*/

        //test creating a template and adding it to the repo
        TestTemplate t1 = new TestTemplate("Template_0x01", "Brief description of the template.", true, 3, 11, 10, 60, 100,500);
        TestTemplate t2 = new TestTemplate("Template_0x02", "Brief description of the template.", true, 4, 15, 10, 60, 100 ,500);
        TestTemplate t3 = new TestTemplate("Template_0x03", "Brief description of the template.", true, 2, 7, 10, 60, 100 ,500);
        TestTemplate t4 = new TestTemplate("Template_0x04", "Brief description of the template.", true, 45, 5, 10, 60, 100 ,500);
        TestTemplate t5 = new TestTemplate("Template_0x05_editable_set_FALSE", "THIS TEMPLATE IS _NOT_ EDITABLE. Copying is possible.",false, 65, 7, 10, 60, 100,500);
        TestTemplate t6 = new TestTemplate("Template_0x06", "Brief description of the template.", true, 85, 5, 10, 60, 100 ,500);
        TestTemplate t7 = new TestTemplate("Template_0x07", "Brief description of the template.", true, 105 , 5, 10, 60, 100 ,500);
        TestTemplate t8 = new TestTemplate("Template_0x08_editable_set_FALSE", "THIS TEMPLATE IS _NOT_ EDITABLE. Copying is possible.", false, 10, 3, 10, 60, 100,500);

        testTemplateService.saveSomeAttributes(t1);
        testTemplateService.saveSomeAttributes(t2);
        testTemplateService.saveSomeAttributes(t3);
        testTemplateService.saveSomeAttributes(t4);
        testTemplateService.saveSomeAttributes(t5);
        testTemplateService.saveSomeAttributes(t6);
        testTemplateService.saveSomeAttributes(t7);
        testTemplateService.saveSomeAttributes(t8);


        //Add devices
        Device d1 = new Device("aDevice", "aType", "aVersion", "aManufacturer", "aDriver");
        d1.setIsUsed();
        d1.setImageId(d1.getDeviceName(), d1.getVersion());
        d1.setImageExtension("jpg");
        d1.setImageFile(d1.getImageId(), d1.getImageExtension());
        d1.setImageFullPath(d1.getImageFile());
        deviceRepository.save(d1);

        Device d2 = new Device("aDevice2", "aType2", "aVersion2", "aManufacturer2", "aDriver2");
        d2.setDisabled();
        d2.setIsUsed();
        // No image
        deviceRepository.save(d2);

        Device d3 = new Device("aDevice3", "aType3", "aVersion3", "aManufacturer3", "aDriver3");
        d3.setIsInUse();
        d3.setImageId(d3.getDeviceName(), d3.getVersion());
        d3.setImageExtension("jpg");
        d3.setImageFile(d3.getImageId(), d3.getImageExtension());
        d3.setImageFullPath(d3.getImageFile());
        deviceRepository.save(d3);

        Device d4 = new Device("aDevice4", "aType4", "aVersion4", "aManufacturer4", "aDriver4");
        d4.setImageId(d4.getDeviceName(), d4.getVersion());
        d4.setImageExtension("jpg");
        d4.setImageFile(d4.getImageId(), d4.getImageExtension());
        d4.setImageFullPath(d4.getImageFile());
        deviceRepository.save(d4);

        //Add test plans
        TestPlan tp1 = new TestPlan("Testplan_0x01","This is a description for testplan 1");
        TestPlan tp2 = new TestPlan("Testplan_0x02","This is a description for testplan 2");
        TestPlan tp3 = new TestPlan("Testplan_0x03","This is a description for testplan 3");

        t5.setEditable(false);   //Add sequences to templates, templates to plans at runtime in order to keep things working.
        tp1.setTestTemplate(t5);
        testTemplateRepository.save(t5);
        List<User> testPlanUsers = new ArrayList<User>();
        testPlanUsers.add(u1);
        testPlanUsers.add(u2);
        testPlanUsers.add(u3);
        tp1.setUsers(testPlanUsers);
        d1.setIsInUse();
        d1.setIsUsed();
        tp1.setDevice(d1);
        deviceRepository.save(d1);
        testPlanRepository.save(tp1);

        t8.setEditable(false);
        testTemplateRepository.save(t8);
        tp2.setTestTemplate(t8);
        testPlanUsers.clear();
        testPlanUsers.add(u2);
        testPlanUsers.add(u3);
        tp2.setUsers(testPlanUsers);
        d2.setIsInUse();
        d2.setIsUsed();
        tp2.setDevice(d2);
        deviceRepository.save(d2);
        testPlanRepository.save(tp2);

        t2.setEditable(false);
        testTemplateRepository.save(t2);
        tp3.setTestTemplate(t2);
        testPlanUsers.clear();
        testPlanUsers.add(u2);
        testPlanUsers.add(u3);
        tp3.setUsers(testPlanUsers);
        d2.setIsInUse();
        d2.setIsUsed();
        tp3.setDevice(d3);
        deviceRepository.save(d3);
        testPlanRepository.save(tp3);

        TestObject to1 = new TestObject("TestObject 1", u2.getUserName(), tp1);
        List<Result> resList1 = new ArrayList<>();
        Result r1 = new Result(5,10,1000,20.53);
        Result r2 = new Result(0.5,7,1200,50.6);
        Result r3 = new Result(3,6.5,980,40);
        Result r4 = new Result(4.3,8,1100,30.6);
        resultRepository.save(r1);
        resultRepository.save(r2);
        resultRepository.save(r3);
        resultRepository.save(r4);
        Result r30 = new Result(4,6.5,890,50.3);
        resultRepository.save(r30);
        List<Long> seqList1 = new ArrayList<>();
        seqList1.add(tp1.getTestTemplate().getTestSequences().get(0).getId());
        seqList1.add(tp1.getTestTemplate().getTestSequences().get(1).getId());
        seqList1.add(tp1.getTestTemplate().getTestSequences().get(2).getId());
        seqList1.add(tp1.getTestTemplate().getTestSequences().get(3).getId());
        resList1.add(r1);
        resList1.add(r2);
        resList1.add(r3);
        resList1.add(r4);
        resList1.add(r30);
        to1.setResults(resList1);
        testObjectRepository.save(to1);



        TestObject to2 = new TestObject("TestObject 2", u3.getUserName(), tp1);
        Result r5 = new Result(6,7,970,38);
        Result r6 = new Result(3.1,6.23,1235,80.3);
        Result r7 = new Result(1.8,9.5,1300,15.2);
        Result r8 = new Result(4,6.8,650,20.9);
        resultRepository.save(r5);
        resultRepository.save(r6);
        resultRepository.save(r7);
        resultRepository.save(r8);
        Result r29 = new Result(4,6.5,890,50.3);
        resultRepository.save(r29);
        List<Long> seqList2 = new ArrayList<>();
        seqList2.add(tp1.getTestTemplate().getTestSequences().get(0).getId());
        seqList2.add(tp1.getTestTemplate().getTestSequences().get(1).getId());
        seqList2.add(tp1.getTestTemplate().getTestSequences().get(2).getId());
        seqList2.add(tp1.getTestTemplate().getTestSequences().get(3).getId());
        to2.setSequences(seqList2);
        List<Result> resList2 = new ArrayList<>();
        resList2.add(r5);
        resList2.add(r6);
        resList2.add(r7);
        resList2.add(r8);
        resList2.add(r29);
        to2.setResults(resList2);
        testObjectRepository.save(to2);

        TestObject to3 = new TestObject("TestObject 3", u2.getUserName(), tp2);
        Result r9 = new Result(5,7,1020,16.3);
        Result r10 = new Result(2,7.23,1500,50.8);
        Result r11 = new Result(1.2,6.5,1300,12.4);
        Result r12 = new Result(4,6.5,778,10.9);
        resultRepository.save(r9);
        resultRepository.save(r10);
        resultRepository.save(r11);
        resultRepository.save(r12);
        Result r28 = new Result(4,6.5,890,50.3);
        resultRepository.save(r28);
        List<Long> seqList3 = new ArrayList<>();
        seqList3.add(tp2.getTestTemplate().getTestSequences().get(0).getId());
        seqList3.add(tp2.getTestTemplate().getTestSequences().get(1).getId());
        seqList3.add(tp2.getTestTemplate().getTestSequences().get(2).getId());
        seqList3.add(tp2.getTestTemplate().getTestSequences().get(3).getId());
        to3.setSequences(seqList3);
        List<Result> resList3 = new ArrayList<>();
        resList3.add(r9);
        resList3.add(r10);
        resList3.add(r11);
        resList3.add(r12);
        resList3.add(r28);
        to3.setResults(resList3);
        testObjectRepository.save(to3);

        TestObject to4 = new TestObject("TestObject 4", u3.getUserName(), tp2);
        Result r13 = new Result(2,6.6,980,10.2);
        Result r14 = new Result(1.5,4.5,1300,30.2);
        Result r15 = new Result(4.7,9,1200,20.6);
        Result r16 = new Result(5.6,7.5,800,80.9);
        resultRepository.save(r13);
        resultRepository.save(r14);
        resultRepository.save(r15);
        resultRepository.save(r16);
        Result r27 = new Result(4,6.5,890,5.3);
        resultRepository.save(r27);
        List<Long> seqList4 = new ArrayList<>();
        seqList4.add(tp2.getTestTemplate().getTestSequences().get(0).getId());
        seqList4.add(tp2.getTestTemplate().getTestSequences().get(1).getId());
        seqList4.add(tp2.getTestTemplate().getTestSequences().get(2).getId());
        seqList4.add(tp2.getTestTemplate().getTestSequences().get(3).getId());
        to4.setSequences(seqList4);
        List<Result> resList4 = new ArrayList<>();
        resList4.add(r13);
        resList4.add(r14);
        resList4.add(r15);
        resList4.add(r16);
        resList4.add(r27);
        to4.setResults(resList4);
        testObjectRepository.save(to4);

        TestObject to5 = new TestObject("TestObject 5", u2.getUserName(), tp3);
        Result r17 = new Result(1,3.8,1350,40.2);
        Result r18 = new Result(1.8,4.5,1300,9);
        Result r19 = new Result(3,8,1020,40.1);
        Result r20 = new Result(4,6.5,890,50.3);
        resultRepository.save(r17);
        resultRepository.save(r18);
        resultRepository.save(r19);
        resultRepository.save(r20);
        Result r26 = new Result(4,6.5,890,50.3);
        resultRepository.save(r26);
        List<Long> seqList5 = new ArrayList<>();
        seqList5.add(tp3.getTestTemplate().getTestSequences().get(0).getId());
        seqList5.add(tp3.getTestTemplate().getTestSequences().get(1).getId());
        seqList5.add(tp3.getTestTemplate().getTestSequences().get(2).getId());
        seqList5.add(tp3.getTestTemplate().getTestSequences().get(3).getId());
        to5.setSequences(seqList5);
        List<Result> resList5 = new ArrayList<>();
        resList5.add(r17);
        resList5.add(r18);
        resList5.add(r19);
        resList5.add(r20);
        resList5.add(r26);
        to5.setResults(resList5);
        testObjectRepository.save(to5);

        TestObject to6 = new TestObject("TestObject 6", u3.getUserName(), tp3);
        Result r21 = new Result(2,3.5,900,60.2);
        Result r22 = new Result(1.5,4.1,970,30.4);
        Result r23 = new Result(3.3,6,870,10.1);
        Result r24 = new Result(0.5,4.8,1000,60.3);
        Result r25 = new Result(0.5,4.8,1000,60.3);
        resultRepository.save(r21);
        resultRepository.save(r22);
        resultRepository.save(r23);
        resultRepository.save(r24);
        resultRepository.save(r25);
        List<Long> seqList6 = new ArrayList<>();
        seqList6.add(tp3.getTestTemplate().getTestSequences().get(0).getId());
        seqList6.add(tp3.getTestTemplate().getTestSequences().get(1).getId());
        seqList6.add(tp3.getTestTemplate().getTestSequences().get(2).getId());
        seqList6.add(tp3.getTestTemplate().getTestSequences().get(3).getId());
        to6.setSequences(seqList6);
        List<Result> resList6 = new ArrayList<>();
        resList5.add(r21);
        resList5.add(r22);
        resList5.add(r23);
        resList5.add(r24);
        resList5.add(r25);
        to6.setResults(resList6);
        testObjectRepository.save(to6);

        Survey s1 = new Survey();
        s1.setOpinion("Zorg dat het personaliseerbaar is");
        s1.setPowerControl("6");
        s1.setDevice("aDevice1");
        s1.setUser("Rudy");
        surveyRepository.save(s1);
        Survey s2 = new Survey();
        s2.setOpinion("Maak de besturing mogelijk met 2 handen");
        s2.setPowerControl("4");
        s2.setDevice("aDevice2");
        s2.setUser("Gunther");
        surveyRepository.save(s2);
        //Survey s3 = new Survey();
        //s3.setOpinion("Geen idee");
        //s3.setPowerControl("1");
        //s3.setDevice("aDevice3");
        //surveyRepository.save(s3);
        //Survey s4 = new Survey();
        //s4.setOpinion("Maak het toestel groter");
        //s4.setPowerControl("2");
        //s4.setDevice(d4);
        //surveyRepository.save(s4);

    }
}