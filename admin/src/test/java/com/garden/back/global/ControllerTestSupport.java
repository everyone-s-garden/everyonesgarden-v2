package com.garden.back.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.back.api.question.QuestionController;
import com.garden.back.domain.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {
        QuestionController.class
    }
)
@ActiveProfiles("test")
public abstract class ControllerTestSupport {

    @Autowired
    WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected QuestionService questionService;

}
