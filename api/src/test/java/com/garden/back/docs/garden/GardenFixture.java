package com.garden.back.docs.garden;

import com.garden.back.garden.controller.dto.request.*;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.garden.facade.GardenDetailFacadeResponse;
import com.garden.back.garden.service.dto.response.*;

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

    public static GardenDetailFacadeResponse gardenDetailResult() {
        return new GardenDetailFacadeResponse(
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
                true,
            1L
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
                                List.of("www.garden.com"),
                                GardenStatus.ACTIVE.name(),
                                GardenType.PUBLIC.name()
                        )
                )
        );
    }

    public static GardenMineResults gardenMineResults() {
        return new GardenMineResults(
                List.of(
                        new GardenMineResults.GardenMineResult(
                                1L,
                                "1000",
                                "영수네 텃밭",
                                "100000",
                                GardenStatus.ACTIVE.name(),
                                List.of("www.garden.com")
                        )
                )
        );
    }

    public static GardenLikeByMemberResults gardenLikeByMemberResults() {
        return new GardenLikeByMemberResults(
                List.of(
                        new GardenLikeByMemberResults.GardenLikeByMemberResult(
                                1L,
                                "1000",
                                "영수네 텃밭",
                                "100000",
                                GardenStatus.ACTIVE.name(),
                                List.of("www.garden.com")
                        )
                )
        );
    }

    public static GardenLikeCreateRequest gardenLikeCreateRequest() {
        return new GardenLikeCreateRequest(1L);
    }

    public static GardenLikeDeleteRequest gardenLikeDeleteRequest() {
        return new GardenLikeDeleteRequest(
                1L
        );
    }

    public static GardenCreateRequest gardenCreateRequest() {
        return new GardenCreateRequest(
                "별이네 텃밭",
                "100",
                "200",
                "ACTIVE",
                "www.everygarden.me",
                "000-000-0000",
                "인천광역시 서구 만수동 200",
                37.444917,
                127.138868,
                true,
                true,
                true,
                "화장실이 깨끗하고 토양의 질이 좋습니다. 모두 놀러오세요",
                "2023.12.01",
                "2023.12.23",
                "2023.12.01",
                "2023.12.31"
        );
    }

    public static GardenUpdateRequest gardenUpdateRequest() {
        return new GardenUpdateRequest(
                List.of("https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg"),
                "별이네 텃밭",
                "100",
                "200",
                "ACTIVE",
                "PRIVATE",
                "www.everygarden.me",
                "000-000-0000",
                "인천광역시 서구 만수동 200",
                37.444917,
                127.138868,
                true,
                true,
                true,
                "화장실이 깨끗하고 흙이 좋아요",
                "2023.12.01",
                "2023.12.23",
                "2023.12.01",
                "2023.12.31"
        );
    }

    public static MyManagedGardenGetResults myManagedGardenGetResults() {
        return new MyManagedGardenGetResults(
                List.of(
                        new MyManagedGardenGetResults.MyManagedGardenGetResult(
                                1L,
                                "별이네 주말농장",
                                "2023.12.01",
                                "2023.12.31",
                                List.of("https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg")
                        )
                )
        );
    }

    public static MyManagedGardenCreateRequest myManagedGardenCreateRequest() {
        return new MyManagedGardenCreateRequest(
                1L,
                "2023.12.01",
                "2023.12.31"
        );
    }

    public static MyManagedGardenUpdateRequest myManagedGardenUpdateRequest() {
        return new MyManagedGardenUpdateRequest(
                1L,
                "2023.12.01",
                "2023.12.31"
        );
    }

    public static MyManagedGardenDetailResult myManagedGardenDetailResult() {
        return new MyManagedGardenDetailResult(
                1L,
                "금쪽이네 텃밭",
                "인천 계양구 계산동 22",
                "2023.12.01",
                "2023.12.31",
                "https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg"
        );
    }

}