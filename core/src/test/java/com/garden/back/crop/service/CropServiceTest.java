package com.garden.back.crop.service;

import com.garden.back.crop.infra.MonthlyRecommendedCropsFetcher;
import com.garden.back.crop.infra.MonthlyRecommendedCropsInfraResponse;
import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import com.garden.back.global.MockTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.in;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class CropServiceTest extends MockTestSupport {

    @Mock
    MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher;

    @InjectMocks
    CropCommandService cropService;

    @Test
    void getMonthlyRecommendedCrops() {
        // given
        int sizeOfMonth = 12;
        MonthlyRecommendedCropsInfraResponse infraResponse = sut.giveMeBuilder(MonthlyRecommendedCropsInfraResponse.class)
            .size("cropsResponses", sizeOfMonth)
            .set("cropsResponses[0].month", 1)
            .set("cropsResponses[1].month", 2)
            .set("cropsResponses[2].month", 3)
            .set("cropsResponses[3].month", 4)
            .set("cropsResponses[4].month", 5)
            .set("cropsResponses[5].month", 6)
            .set("cropsResponses[6].month", 7)
            .set("cropsResponses[7].month", 8)
            .set("cropsResponses[8].month", 9)
            .set("cropsResponses[9].month", 10)
            .set("cropsResponses[10].month", 11)
            .set("cropsResponses[11].month", 12)
            .sample();

        given(monthlyRecommendedCropsFetcher.getMonthlyRecommendedCrops()).willReturn(infraResponse);

        // when
        MonthlyRecommendedCropsResponse actualResponse = cropService.getMonthlyRecommendedCrops();

        // then
        then(actualResponse)
            .as("검증할 응답 객체")
            .isNotNull()
            .satisfies(response -> {
                then(response.cropsResponses())
                    .as("작물 응답 리스트")
                    .hasSize(12)
                    .allSatisfy(cropResponse ->
                        then(cropResponse.month()).isBetween(1, 12));
            });
    }
}