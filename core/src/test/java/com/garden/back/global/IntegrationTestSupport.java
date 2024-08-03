package com.garden.back.global;

import com.amazonaws.services.s3.AmazonS3;
import com.garden.back.crop.infra.MonthlyRecommendedCropsFetcher;
import com.garden.back.global.image.ImageUploader;
import com.garden.back.global.image.NCPProperties;
import com.garden.back.global.image.ParallelImageUploader;
import com.garden.back.weather.infra.api.naver.NaverGeoClient;
import com.garden.back.weather.infra.api.open.OpenAPIClient;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)
@Execution(ExecutionMode.SAME_THREAD)
public abstract class IntegrationTestSupport extends FixtureSupport {

    @MockBean
    protected OpenAPIClient openAPIClient;

    @MockBean
    protected NaverGeoClient naverGeoClient;

    @MockBean
    protected ImageUploader imageUploader;

    @MockBean
    protected ParallelImageUploader parallelImageUploader;

    @MockBean
    NCPProperties ncpProperties;

    @MockBean
    AmazonS3 ncpUploader;

    @MockBean
    protected MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher;

}
