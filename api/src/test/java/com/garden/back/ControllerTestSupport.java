package com.garden.back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garden.back.garden.GardenController;
import com.garden.back.garden.service.GardenCommandService;
import com.garden.back.garden.service.GardenReadService;
import com.garden.back.global.FixtureSupport;
import com.garden.back.report.ReportController;
import com.garden.back.report.service.ReportService;
import com.garden.back.weather.WeatherController;
import com.garden.back.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {
        GardenController.class,
        WeatherController.class,
        ReportController.class
    }
)
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

}
