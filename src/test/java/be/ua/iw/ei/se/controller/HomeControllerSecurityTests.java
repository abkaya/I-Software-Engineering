package be.ua.iw.ei.se.controller;

import be.ua.iw.ei.se.IdTestManagerApplication;
import be.ua.iw.ei.se.model.User;
import be.ua.iw.ei.se.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;


/**
 * Created by Edwin on 19/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IdTestManagerApplication.class)
@WebAppConfiguration
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
    @WithMockUser(username = "ravan")
    public void testThree() {
        homeController.showHomepage(new ModelMap());
    }
    @Test
    @WithUserDetails("Edwin")
    public void testFour() {
        homeController.showHomepage(new ModelMap());
    }
}
