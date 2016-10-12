package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HomeController.class, secure = false)
public class HomeControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    @Test
    public void viewHomepageTest() throws Exception {
//        mvc.perform(get("/home"))
//               .andExpect(view().name("homepage"));
    }
}