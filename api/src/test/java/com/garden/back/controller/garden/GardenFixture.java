package com.garden.back.controller.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.global.GeometryUtil;

import java.time.LocalDate;

public class GardenFixture {

    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;
    private static final LocalDate RECRUIT_START_DATE = LocalDate.of(2023,11,1);
    private static final LocalDate RECRUIT_END_DATE = LocalDate.of(2023,12,7);
    private static final LocalDate USE_START_DATE = LocalDate.of(2024,12,7);
    private static final LocalDate USE_END_DATE = LocalDate.of(2024,12,15);
    private GardenFixture() {
        throw new RuntimeException("생성자를 통해 객체를 만들 수 없습니다.");
    }

    public static Garden privateGarden() {
        return Garden.of(
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
            "모두의 텃밭",
            GardenType.PRIVATE,
            GardenStatus.ACTIVE,
            "www.everygarden.me",
            "100",
            "000-000-000",
            "200.23",
            "화장실이 깨끗하고 농기구를 빌려줍니다.",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            USE_START_DATE,
            USE_END_DATE,
            true,
            false,
            true,
            1L,
            false,
            0
        );
    }
}
