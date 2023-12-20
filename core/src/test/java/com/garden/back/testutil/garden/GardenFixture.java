package com.garden.back.testutil.garden;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.garden.service.dto.request.GardenByComplexesParam;
import com.garden.back.garden.service.dto.request.GardenDetailParam;
import com.garden.back.garden.service.dto.request.GardenGetAllParam;
import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.garden.util.GeometryUtil;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GardenFixture {

    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

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
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                LocalDateTime.now().plusDays(31),
                LocalDateTime.now().plusMonths(6),
                true,
                false,
                true,
                1L,
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

    public static GardenByComplexesParam publicGardenByComplexesParam() {
        return new GardenByComplexesParam(
                GardenType.PUBLIC.name(),
                0,
                LATITUDE,
                LONGITUDE,
                LATITUDE + 2,
                LONGITUDE + 2
        );
    }

    public static GardenByComplexesParam allGardenByComplexesParam() {
        return new GardenByComplexesParam(
                GardenType.All.name(),
                0,
                LATITUDE,
                LONGITUDE,
                LATITUDE + 2,
                LONGITUDE + 2
        );
    }

    public static GardenByComplexesParam privateGardenByComplexesParam() {
        return new GardenByComplexesParam(
                GardenType.PRIVATE.name(),
                0,
                LATITUDE,
                LONGITUDE,
                LATITUDE + 2,
                LONGITUDE + 2
        );
    }

    public static Garden randomPublicGardenWithinComplexes(Point point) {
        return Garden.of(
                "인천광역시 서구 만수동 200",
                point.getY(),
                point.getX(),
                point,
                "별이네 텃밭",
                GardenType.PUBLIC,
                GardenStatus.ACTIVE,
                "www.everygarden.me",
                "100",
                "000-000-000",
                "200",
                "화장실이 깨끗해요 그리고 시설이 최신식입니다.",
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

    public static GardenDetailParam gardenDetailParam(Long gardenId) {
        return new GardenDetailParam(
                1L,
                gardenId
        );
    }

    public static RecentViewGarden publicRecentViewGarden() {
        return new RecentViewGarden(
                1L,
                "100",
                "도연이네",
                "www.everyGarden.com",
                "10000",
                GardenStatus.ACTIVE.name(),
                GardenType.PUBLIC.name()
        );
    }

    public static RecentViewGarden privateRecentViewGarden() {
        return new RecentViewGarden(
                1L,
                "100",
                "도연이네",
                "www.everyGarden.com",
                "10000",
                GardenStatus.ACTIVE.name(),
                GardenType.PRIVATE.name()
        );
    }

}
