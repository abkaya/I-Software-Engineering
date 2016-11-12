package be.uantwerpen.fti.se.controller;

import be.uantwerpen.fti.se.repository.DeviceRepository;
import be.uantwerpen.fti.se.service.DeviceService;
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
public class DeviceControllerTests {
    @InjectMocks
    private DeviceController deviceController;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceService deviceService;

    private MockMvc mvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(deviceController).build();
    }

    @Test
    public void viewDevicesList() throws Exception {
        mvc.perform(get("/devices")).andExpect(view().name("devices-list"));
    }

    @Test
    public void viewManageDevices() throws Exception {
        mvc.perform(get("/devices/put")).andExpect(view().name("devices-manage"));
    }

    @Test
    public void viewDeleteDevices() throws Exception {
        mvc.perform(get("/devices/1/delete")).andExpect(view().name("devices-list"));
    }

}


