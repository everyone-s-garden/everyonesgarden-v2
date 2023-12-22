package com.garden.back.docs.garden;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.garden.service.dto.request.GardenByNameParam;
import com.garden.back.garden.service.dto.response.*;
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

        return new GardenByComplexesResults(gardenByComplexesResults, false);
    }

    public static GardenDetailResult gardenDetailResult() {
        return new GardenDetailResult(
                1L,
                "인천광역시 서구 신현동 222-22",
                37.4449168,
                127.1388684,
                "진겸이네 주말농장",
                GardenType.PUBLIC.name(),
                "www.studay.me",
                "100000",
                "000-0000-0000",
                "1000",
                GardenStatus.ACTIVE.name(),
                1L,
                "2023.12.01",
                "2023.12.25",
                "2024.01.01",
                "2024.12.25",
                "농기구를 빌릴 수 있는 자판기가 있습니다. 작물 키우는 법도 알려드려요",
                List.of("www.garden.com"),
                new GardenDetailResult.GardenFacility(
                        false,
                        true,
                        true
                ),
                true
        );
    }

    public static RecentGardenResults recentGardenResults() {
        return new RecentGardenResults(
                List.of(
                        new RecentGardenResults.RecentGardenResult(
                                1L,
                                "1000",
                                "영수네 텃밭",
                                "100000",
                                "www.garden.com",
                                GardenStatus.ACTIVE.name(),
                                GardenType.PUBLIC.name()
                        )
                )
        );
    }
}
