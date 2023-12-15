package com.garden.back.global;

import com.garden.back.weather.infra.api.naver.NaverGeoClient;
import com.garden.back.weather.infra.api.open.OpenAPIClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport extends FixtureSupport {

    @MockBean
    protected OpenAPIClient openAPIClient;

    @MockBean
    protected NaverGeoClient naverGeoClient;
}