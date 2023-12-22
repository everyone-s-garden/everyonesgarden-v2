package com.garden.back.docs.garden;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.response.GardenAllResults;
import com.garden.back.garden.service.dto.response.GardenByComplexesResults;
import com.garden.back.garden.service.dto.response.GardenByNameResults;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class GardenFixture {
    public static GardenByNameResults gardenByNameResults() {
        List<GardenByNameResults.GardenByNameResult> gardensByName = List.of(
                new GardenByNameResults.GardenByNameResult(1L, "도연이네 텃밭", "제주도 서귀포"),
                new GardenByNameResults.GardenByNameResult(2L, "도연이네 주말농장", "경기도 수정시 분당구")
        );

        return new GardenByNameResults(gardensByName, false);
    }

    public static GardenAllResults gardenAllResults() {
        List<GardenAllResults.GardenAllResult> gardenAllResults = List.of(
                new GardenAllResults.GardenAllResult(
                        1L,
                        37.4449168,
                        127.1388684,
                        "별이네 텃밭",
                        GardenType.PUBLIC.name(),
                        "100000",
                        "100",
                        GardenStatus.ACTIVE.name(),
                        List.of("www.garden.com")
                )
        );

        return new GardenAllResults(gardenAllResults, false);
    }

    public static GardenByComplexesResults gardenByComplexesResults() {
        List<GardenByComplexesResults.GardenByComplexesResult> gardenByComplexesResults =
                List.of(new GardenByComplexesResults.GardenByComplexesResult(
                                1L,
                                "100",
                                "별이네 텃밭",
                                "100000",
                                List.of("www.garden.com"),
                                GardenStatus.ACTIVE.name(),
                                GardenType.PUBLIC.name(),
                                37.4449168,
                                127.1388684
                        )
                );

        return new GardenByComplexesResults(gardenByComplexesResults,false);

    }
}
