package com.garden.back.crop.infra.wiki;

import com.garden.back.crop.infra.MonthlyRecommendedCropsInfraResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WikiRecommendedCropsImplTest {


    WikiRecommendedCropsImpl wikiRecommendedCrops = new WikiRecommendedCropsImpl(new MonthlyWikiRecommendedCropsRepository());

    @DisplayName("1월부터12월까지의 month 값이 예상과 일치하는지 확인한다.")
    @Test
    void getMonthlyRecommendedCrops() {
        MonthlyRecommendedCropsInfraResponse response = wikiRecommendedCrops.getMonthlyRecommendedCrops();

        assertThat(response).isNotNull();
        assertThat(response.cropsResponses().size()).isEqualTo(12);

        for (int month = 1; month <= 12; month++) {
            int finalMonth = month;
            boolean monthExists = response.cropsResponses().stream()
                .anyMatch(cropsResponse -> cropsResponse.month().equals(finalMonth)); //람다 내부에는 final 값이 들어가야 함
            assertThat(monthExists).isTrue();
        }
    }
}