package com.garden.back.global;

import com.garden.back.global.image.ImageUploader;
import com.garden.back.weather.infra.api.naver.NaverGeoClient;
import com.garden.back.weather.infra.api.open.OpenAPIClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)
public abstract class IntegrationTestSupport extends FixtureSupport {

    @MockBean
    protected OpenAPIClient openAPIClient;

    @MockBean
    protected NaverGeoClient naverGeoClient;

    @MockBean
    protected ImageUploader imageUploader;

}