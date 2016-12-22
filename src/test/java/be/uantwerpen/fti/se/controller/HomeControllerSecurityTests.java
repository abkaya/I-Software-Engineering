package be.uantwerpen.fti.se.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;


/**
 * Created by Edwin on 19/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerSecurityTests {
    @Autowired
    private HomeController homeController;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testOne() {
        homeController.showHomepage(new ModelMap());
    }
    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testTwo() {
        homeController.showHomepage(new ModelMap());
    }
    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "idonotexist")
    public void testThree() {
        homeController.showHomepage(new ModelMap());
    }
   /* @Test
    @WithUserDetails("admin")
    public void testFour() {
        homeController.showHomepage(new ModelMap());
    }*/
}
