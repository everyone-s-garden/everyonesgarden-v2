package com.garden.back.crop.infra.wiki;

import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MonthlyWikiRecommendedCropsRepositoryTest extends IntegrationTestSupport {

    @Autowired
    MonthlyWikiRecommendedCropsRepository monthlyWikiRecommendedCropsRepository;

    @DisplayName("위키에 있는 내용을 초기화 한다.")
    @Test
    void initWiki() {
        assertThat(monthlyWikiRecommendedCropsRepository.findByMonth(1)).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    @DisplayName("1월부터 12월까지 각 월의 작물을 조회할 수 있다.")
    void findByMonth(int month) {
        List<WikiRecommendedCrop> crops = monthlyWikiRecommendedCropsRepository.findByMonth(month);
        assertThat(crops).isNotEmpty();
    }

    @DisplayName("유효하지 않은 달의 작물은 조회가 안된다.")
    @Test
    void findByMonth_ShouldReturnEmptyListForInvalidMonth() {
        List<WikiRecommendedCrop> crops = monthlyWikiRecommendedCropsRepository.findByMonth(13);

        assertThat(crops)
            .as("Check if crop list for invalid month is empty")
            .isEmpty();
    }
}