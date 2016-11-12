package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.TestSequenceRepository;
import be.uantwerpen.fti.se.service.TestSequenceService;
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
public class TestSequenceControllerTest {
    @InjectMocks
    private TestSequenceController testSequenceController;

    @Mock
    private TestSequenceRepository testSequenceRepository;

    @Mock
    private TestSequenceService testSequenceService;

    private MockMvc mvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(testSequenceController).build();
    }

    @Test
    public void viewTestSequenceList() throws Exception {
        mvc.perform(get("/testSequences")).andExpect(view().name("testSequences-list"));
    }

    @Test
    public void viewManageTestSequence() throws Exception {
        mvc.perform(get("/testSequences/put")).andExpect(view().name("testSequences-manage"));
    }

    @Test
    public void viewManageIdTestSequence() throws Exception {
        mvc.perform(get("/testSequence/1")).andExpect(view().name("testSequences-manage"));
    }

    @Test
    public void viewDeleteTestSequence() throws Exception {
        mvc.perform(get("/testSequence/1/delete")).andExpect(view().name("redirect:/testSequences"));
    }
}
