package com.garden.back.weather.infra.api.open;

import com.garden.back.global.MockTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RegionProviderTest extends MockTestSupport {

    @InjectMocks
    RegionProvider regionProvider;

    @BeforeEach
    public void setUp() {
        regionProvider.setUp();
    }

    @DisplayName("17개의 시, 도의 정보를 공공 데이터 api에 맞게 초기화 한다.")
    @Test
    void findAll() {
        int totalSize = 17;

        assertThat(regionProvider.findAll())
            .isNotNull()
            .isNotEmpty()
            .hasSize(totalSize);
    }

    @DisplayName("지역 이름으로 지역 정보를 조회할 수 있다.")
    @Test
    void findRegionByName() {
        String regionName = "서울특별시";
        Optional<Region> foundRegion = regionProvider.findRegionByName(regionName);
        assertThat(foundRegion)
            .isPresent()
            .hasValueSatisfying(region -> assertThat(region.regionName()).isEqualTo(regionName));
    }

    @DisplayName("잘못 된 지역으로 지역 정보를 조회시 빈 Optional값이 반환된다.")
    @Test
    void findRegionByNameEmpty() {
        String invalidRegionName = "InvalidRegion";
        Optional<Region> invalidRegion = regionProvider.findRegionByName(invalidRegionName);
        assertThat(invalidRegion).isNotPresent();
    }
}