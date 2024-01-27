package com.garden.back;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.garden.back.chat.cropchat.controller.CropChatRoomController;
import com.garden.back.chat.gardenchat.controller.GardenChatRoomController;
import com.garden.back.chat.gardenchat.facade.ChatRoomFacade;
import com.garden.back.crop.CropController;
import com.garden.back.crop.service.CropQueryService;
import com.garden.back.crop.service.CropCommandService;
import com.garden.back.feedback.FeedbackController;
import com.garden.back.feedback.service.FeedbackService;
import com.garden.back.garden.GardenController;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.global.FixtureSupport;
import com.garden.back.global.TestSecurityConfig;
import com.garden.back.post.PostController;
import com.garden.back.post.service.PostCommandService;
import com.garden.back.post.service.PostQueryService;
import com.garden.back.report.ReportController;
import com.garden.back.report.service.ReportService;
import com.garden.back.crop.service.CropChatRoomService;
import com.garden.back.garden.service.GardenChatRoomService;
import com.garden.back.weather.WeatherController;
import com.garden.back.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {
        GardenController.class,
        WeatherController.class,
        ReportController.class,
        FeedbackController.class,
        GardenChatRoomController.class,
        CropChatRoomController.class,
        PostController.class,
        CropController.class
    }
)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class ControllerTestSupport extends FixtureSupport {

    @Autowired
    WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected GardenReadService gardenReadService;

    @MockBean
    protected GardenCommandService gardenCommandService;

    @MockBean
    protected WeatherService weatherService;

    @MockBean
    protected ReportService reportService;

    @MockBean
    protected FeedbackService feedbackService;

    @MockBean
    protected GardenChatRoomService gardenChatRoomService;

    @MockBean
    protected CropChatRoomService chatRoomService;

    @MockBean
    protected ChatRoomFacade chatRoomFacade;

    @MockBean
    protected PostCommandService postCommandService;

    @MockBean
    protected PostQueryService postQueryService;

    @MockBean
    protected CropQueryService cropQueryService;

    @MockBean
    protected CropCommandService cropCommandService;

}
