package com.garden.back.garden;

import com.garden.back.garden.domain.OpenAPIGarden;
import com.garden.back.garden.repository.garden.GardenRepository;
import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import com.garden.back.garden.repository.openapigarden.OpenAPIGardenJpaRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled("현재 틀만 구축해놓은 상태입니다.")
class OpenAPIGardenInfoClientTest {

    @Autowired
    OpenAPIGardenDataFetcher openAPIGardenDataFetcher;

    @Autowired
    OpenAPIGardenJpaRepository openAPIGardenJpaRepository;

    @Autowired
    GardenRepository gardenRepository;

    @DisplayName("공공데이터에 등록된 텃밭 정보를 저장한다.")
    @Test
    void savePublicRegisteredGardens() {
        //given & when
        openAPIGardenDataFetcher.run();
        Slice<GardenGetAll> allGardens = gardenRepository.getAllGardens(Pageable.ofSize(Integer.MAX_VALUE));

        // then
        assertThat(openAPIGardenJpaRepository.findAll()).hasSize(allGardens.getSize());
    }
}
