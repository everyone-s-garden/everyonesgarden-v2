package com.garden.back.garden;

import com.garden.back.garden.model.PublicDataGarden;
import com.garden.back.garden.repository.publicgarden.PublicDataGardenRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OpenAPIGardenInfoClientTest {

    @Autowired
    OpenAPIGardenInfoClient client;

    @Autowired
    PublicDataGardenRepository publicDataGardenRepository;

    @DisplayName("공공데이터에 등록된 텃밭 정보를 저장한다.")
    @Test
    @Disabled("현재 틀만 구축해놓은 상태입니다.")
    void savePublicRegisteredGardens() {
        var result = client.getFarmFromOpnAPI().Grid_20171122000000000552_1().row();
        List<PublicDataGarden> publicDataGardenList = new ArrayList<>();
        for (var row : result) {
            if (row.FARM_NM().contains("보안") || row.FARM_NM().isBlank()) {
                continue;
            }
            publicDataGardenList.add(row.toEntity());
        }

        publicDataGardenRepository.saveAll(publicDataGardenList);
        assertThat(publicDataGardenRepository.findAll()).hasSize(728);
    }
}