package be.uantwerpen.fti.se.service;

import be.uantwerpen.fti.se.model.Permission;
import be.uantwerpen.fti.se.model.Role;
import be.uantwerpen.fti.se.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Edwin on 19/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTests {
    @InjectMocks
    SecurityService securityService;

    @Mock
    private UserService userService;

    List<User> userList;

    @Before
    public void init() {

        Permission p1 = new Permission("logon");
        Permission p2 = new Permission("secret-message");
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");
        List<Permission> permissions =  new ArrayList<Permission>();
        permissions.add(p1);
        tester.setPermissions(permissions);
        permissions =  new ArrayList<Permission>();
        permissions.add(p1);
        permissions.add(p2);
        administrator.setPermissions(permissions);

        User u1 = new User("U","1");
        u1.setUserName("username");
//        u1.setRoles(Arrays.asList(administrator));
        User u2 = new User("U","2");
        u2.setUserName("username");
//        u2.setRoles(Arrays.asList(tester));
        userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void nonExistingUsernameTest() {
        when(userService.findByUserName("")).thenReturn(null);
        securityService.loadUserByUsername("bla");
    }

}
