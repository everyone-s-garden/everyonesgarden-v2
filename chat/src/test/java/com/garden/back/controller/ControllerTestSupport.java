package com.garden.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.back.global.TestSecurityConfig;
import com.garden.back.service.garden.GardenChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
        controllers = {
                ChatRoomController.class
        }
)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class ControllerTestSupport {
    @Autowired
    WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected GardenChatRoomService gardenChatRoomService;
}
