package com.garden.back.region;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GeoDataParserTest {

    @Autowired
    GeoDataParser geoDataParser;

    @Autowired
    RegionRepository regionRepository;

    @DisplayName("대한민국 전국의 법정동 데이터를 추가한다.")
    @Test
    @Disabled("db에 데이터를 넣는 코드라 제거했습니다.")
    void saveAllKoreanRegionWithMultipolygonAndBeopjeongdong() {
        geoDataParser.saveAllKoreanRegionWithMultipolygonAndBeopjeongdong();
        assertThat(regionRepository.findAll()).hasSize(5053);
    }

    @DisplayName("대한민국의 변경된 법정된 데이터를 추가한다.")
    @Test
    @Disabled("db에 데이터를 넣는 코드라 제거했습니다.")
    void saveChangedRegions() {
        geoDataParser.saveChangedRegions();
        assertThat(regionRepository.findAll()).hasSize(7);
    }
}