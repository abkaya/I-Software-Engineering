package be.uantwerpen.fti.se.data;

import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.model.TestSequence;
import be.uantwerpen.fti.se.model.TestTemplate;
import be.uantwerpen.fti.se.model.User;
import be.uantwerpen.fti.se.repository.PermissionRepository;
import be.uantwerpen.fti.se.repository.RoleRepository;
import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import be.uantwerpen.fti.se.repository.UserRepository;
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
    p rivate final TestSequenceRepository testSequenceRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, TestTemplateRepository testTemplateRepository,TestSequenceRepository testSequenceRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.testTemplateRepository = testTemplateRepository;
        this.testSequenceRepository = testSequenceRepository;
    }

    @PostConstruct
    private void initDatabase() {
        //Array of permissions, to be saved in p and later to be assigned to the administrator role.
        String[] allPermissions = {"user-view","user-create","user-edit","user-delete",
                "role-view","role-create","role-edit","role-delete","test-view","test-create","test-edit","test-delete"};
        for (String p : allPermissions){
            permissionRepository.save(new Permission(p));
        }

        //create logon permission and save it to the repository
        Permission p1 = new Permission("logon");
        permissionRepository.save(p1);

        //create admin and tester roles
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");

        //add permission "logon" to the newly created list permissions.
        List<Permission> permissions =  new ArrayList<Permission>();
        permissions.add(p1);

        //now add all permissions in permissions to the tester role (currently just 1)
        tester.setPermissions(permissions);
        roleRepository.save(tester);

        //now add all permissions from the String array above (allPermissions) to the newly created permissions.
        permissions =  new ArrayList<Permission>();
        for (Permission p : permissionRepository.findAll()){
            permissions.add(p);
        }

        //add all these permissions to the administrator role
        administrator.setPermissions(permissions);
        roleRepository.save(administrator);

        //create users and set roles
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

        //test creating a template and adding it to the repo
        TestTemplate t1 = new TestTemplate("Template_0x01", "Brief description of the template.", 0);
        testTemplateRepository.save(t1);


        TestSequence sequence = new TestSequence(2, 11);
        ArrayList<ArrayList<Integer>> firsttest = sequence.CreateSequence();
        sequence.setSequences(firsttest);
        testSequenceRepository.save(sequence);
        TestSequence sequence1 = new TestSequence(5,13);
        firsttest = sequence1.CreateSequence();
        sequence1.setSequences(firsttest);
        testSequenceRepository.save(sequence1);
    }
}