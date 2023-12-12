package com.garden.back.garden.service;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.repository.GardenRepository;
import com.garden.back.garden.service.dto.GardenByNameParam;
import com.garden.back.garden.service.dto.GardenByNameResults;
import com.garden.back.testutil.garden.GardenFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@Import(TestDatabaseConfig.class)
@SpringBootTest(webEnvironment = NONE)
public class GardenReadServiceTest {

    private static final String GARDEN_NAME_FOR_SEARCH = "진겸";
    private static final int INIT_PAGE_NUMBER = 0;
    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private GardenReadService gardenReadService;

    private Garden savedGarden;

    @BeforeEach
    void setUp() {
        savedGarden = gardenRepository.save(GardenFixture.garden());
    }

    @Test
    @DisplayName("텃밭 이름으로 검색했을 때 해당 텃밭이 검색 목록에 포함되는지 확인한다.")
    void getGardensByName_gardenName_containsTrue() {
        // Given
        GardenByNameParam gardenByNameParam = GardenFixture.gardenByNameParam();

        // WHEN
        GardenByNameResults gardensByName = gardenReadService.getGardensByName(gardenByNameParam);

        // THEN
        gardensByName.gardensByName().forEach(
                gardenByNameResult -> {
                    assertThat(gardenByNameResult.gardenName()).isEqualTo(savedGarden.getGardenName());
                    assertThat(gardenByNameResult.gardenId()).isEqualTo(savedGarden.getGardenId());
                    assertThat(gardenByNameResult.address()).isEqualTo(savedGarden.getAddress());
                }
        );

    }
}
