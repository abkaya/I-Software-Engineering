package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.SurveyRepository;
import be.uantwerpen.fti.se.service.SurveyService;
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
 * Created by Kevin on 28/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SurveyControllerTests {
    @InjectMocks
    private SurveyController surveyController;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private SurveyService surveyService;

    private MockMvc mvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(surveyController).build();
    }

    @Test
    public void viewSurveyList() throws Exception {
        mvc.perform(get("/resultssurvey")).andExpect(view().name("surveys-list"));
    }

    @Test
    public void viewSurveyQuestionsList() throws Exception {
        mvc.perform(get("/questionssurvey")).andExpect(view().name("survey-questions"));
    }

}
