package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTests {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private UserService userService;

    private MockMvc mvc;
    @Before
    public void setup()
    {

        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void viewHomepageTest() throws Exception {
        mvc.perform(get("/home"))
               .andExpect(view().name("homepage"));
    }
}