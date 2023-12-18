package com.garden.back.global;

import com.garden.back.global.event.Events;
import com.garden.back.weather.infra.api.naver.NaverGeoClient;
import com.garden.back.weather.infra.api.open.OpenAPIClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)
public abstract class IntegrationTestSupport extends FixtureSupport {

    @MockBean
    protected OpenAPIClient openAPIClient;

    @MockBean
    protected NaverGeoClient naverGeoClient;

    @MockBean
    protected ApplicationEventPublisher eventPublisher;
}