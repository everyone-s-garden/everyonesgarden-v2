package com.garden.back.testutil.garden;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.garden.service.dto.request.GardenGetAllParam;
import com.garden.back.garden.util.GeometryUtil;

import java.time.LocalDateTime;

public class GardenFixture {

    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;

    private GardenFixture() {
        throw new RuntimeException("생성자를 통해 객체를 만들 수 없습니다.");
    }

    public static Garden privateGarden() {
        return new Garden(
                "인천광역시 서구 만수동 200",
                LATITUDE,
                LONGITUDE,
                GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                "모두의 텃밭",
                GardenType.PRIVATE,
                GardenStatus.ACTIVE,
                "www.everygarden.me",
                "100.00",
                "000-000-000",
                "200",
                "화장실이 깨끗해요",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                LocalDateTime.now().plusDays(31),
                LocalDateTime.now().plusMonths(6),
                true,
                false,
                true,
                null,
                false,
                0
        );
    }

    public static Garden publicGarden() {
        return new Garden(
                "인천광역시 서구 만수동 200",
                LATITUDE,
                LONGITUDE,
                GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                "도연이네 텃밭",
                GardenType.PUBLIC,
                GardenStatus.ACTIVE,
                "www.everygarden.me",
                "100.00",
                "000-000-000",
                "200",
                "화장실이 깨끗해요",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                LocalDateTime.now().plusDays(31),
                LocalDateTime.now().plusMonths(6),
                true,
                false,
                true,
                null,
                false,
                0
        );
    }

    public static GardenGetAllParam gardenGetAllParam() {
        return new GardenGetAllParam(
                0,
                1L
        );
    }

}
