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
    @Disabled
    void saveAllKoreanRegionWithMultipolygonAndBeopjeongdong() {
        geoDataParser.saveAllKoreanRegionWithMultipolygonAndBeopjeongdong();
        assertThat(regionRepository.findAll()).hasSize(5054);
    }
}