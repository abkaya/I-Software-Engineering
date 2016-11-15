package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.repository.TestTemplateRepository;
import be.uantwerpen.fti.se.service.TestTemplateService;
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

/**
 * Created by Kevin on 12/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestTemplateControllerTests {

    @InjectMocks
    private TestTemplateController testTemplateController;

    @Mock
    private TestTemplateRepository testTemplateRepository;

    @Mock
    private TestTemplateService testTemplateService;

    @Mock
    private TestSequenceRepository testSequenceRepository;

    private MockMvc mvc;
    @Before
    public void setup()
    {

        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.standaloneSetup(testTemplateController).build();
    }

    @Test
    public void viewTemplateList() throws Exception {
        mvc.perform(get("/tests")).andExpect(view().name("testTemplates-list"));
    }

    @Test
    public void viewManageTemplate() throws Exception {
        mvc.perform(get("/tests/put")).andExpect(view().name("testTemplates-manage"));
    }
}
