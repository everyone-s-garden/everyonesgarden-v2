package com.garden.back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.back.garden.GardenController;
import com.garden.back.garden.service.GardenReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {
        GardenController.class
})
public class ControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected GardenReadService gardenReadService;

}
