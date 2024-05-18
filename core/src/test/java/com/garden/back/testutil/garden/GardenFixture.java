package com.garden.back.testutil.garden;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.GardenImage;
import com.garden.back.garden.domain.GardenLike;
import com.garden.back.garden.domain.MyManagedGarden;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.garden.service.dto.request.*;
import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.global.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class GardenFixture {
    private static final String FIRST_GARDEN_IMAGE_URL = "https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg";
    private static final String SECOND_GARDEN_IMAGE_URL = "https://kr.object.ncloudstorage.com/every-garden/images/garden/view.jpg";
    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;
    private static final LocalDate RECRUIT_START_DATE = LocalDate.of(2023, 11, 1);
    private static final LocalDate RECRUIT_END_DATE = LocalDate.of(2023, 12, 7);
    private static final LocalDate USE_START_DATE = LocalDate.of(2023, 11, 1);
    private static final LocalDate USE_END_DATE = LocalDate.of(2023, 12, 7);

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
            "100",
            "000-000-000",
            "200.23",
            "화장실이 깨끗하고 농기구를 빌려줍니다.",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            true,
            false,
            true,
            1L,
            false,
            0
        );
    }

    public static Garden publicGarden() {
        return Garden.of(
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            GeometryUtil.createPoint(LATITUDE, LONGITUDE),
            "김별이네 텃밭",
            GardenType.PUBLIC,
            GardenStatus.ACTIVE,
            "100",
            "000-000-000",
            "200.23",
            "화장실이 깨끗하고 농기구를 빌려줍니다.",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            true,
            false,
            true,
            1L,
            false,
            0
        );
    }

    public static GardenImage firstGardenImage(Garden garden) {
        return GardenImage.of(FIRST_GARDEN_IMAGE_URL, garden);
    }

    public static GardenImage secondGardenImage(Garden garden) {
        return GardenImage.of(SECOND_GARDEN_IMAGE_URL, garden);
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
            GardenType.ALL.name(),
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
            "100",
            "000-000-000",
            "200",
            "화장실이 깨끗해요 그리고 시설이 최신식입니다.",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
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
            37.123,
            127.123,
            "100",
            "도연이네",
            "10000",
            List.of("www.everyGarden.com"),
            GardenStatus.ACTIVE.name(),
            GardenType.PUBLIC.name()
        );
    }

    public static RecentViewGarden privateRecentViewGarden() {
        return new RecentViewGarden(
            1L,
            37.123,
            127.123,
            "100",
            "도연이네",
            "10000",
            List.of("www.everyGarden.com"),
            GardenStatus.ACTIVE.name(),
            GardenType.PRIVATE.name()
        );
    }

    public static GardenCreateParam gardenCreateParam(String expectedUrl) {
        MultipartFile multipartFile = new MockMultipartFile("test", expectedUrl.getBytes());

        return new GardenCreateParam(
            List.of(multipartFile),
            "별이네 텃밭",
            "100",
            "200",
            GardenStatus.ACTIVE,
            "000-000-0000",
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            new GardenCreateParam.GardenFacility(
                true,
                true,
                true
            ),
            "화장실이 깨끗하고 흙이 좋아요",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            1L
        );
    }

    public static GardenCreateParam gardenCreateParam() {
        return new GardenCreateParam(
            Collections.emptyList(),
            "별이네 텃밭",
            "100",
            "200",
            GardenStatus.ACTIVE,
            "000-000-0000",
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            new GardenCreateParam.GardenFacility(
                true,
                true,
                true
            ),
            "화장실이 깨끗하고 흙이 좋아요",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            1L
        );
    }

    public static GardenUpdateParam gardenUpdateParam(String expectedUrl, Long gardenId) {
        MultipartFile multipartFile = new MockMultipartFile("test", expectedUrl.getBytes());

        return new GardenUpdateParam(
            gardenId,
            List.of(FIRST_GARDEN_IMAGE_URL),
            List.of(multipartFile),
            "별이네 텃밭",
            "100",
            "200",
            GardenStatus.ACTIVE,
            GardenType.PRIVATE,
            "000-000-0000",
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            new GardenUpdateParam.GardenFacility(
                true,
                true,
                true
            ),
            "화장실이 깨끗하고 흙이 좋아요",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            1L
        );
    }

    public static GardenUpdateParam gardenUpdateParam(Long gardenId) {
        return new GardenUpdateParam(
            gardenId,
            List.of(FIRST_GARDEN_IMAGE_URL),
            Collections.emptyList(),
            "별이네 텃밭",
            "100",
            "200",
            GardenStatus.ACTIVE,
            GardenType.PRIVATE,
            "000-000-0000",
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            new GardenUpdateParam.GardenFacility(
                true,
                true,
                true
            ),
            "화장실이 깨끗하고 흙이 좋아요",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            1L
        );
    }

    public static GardenUpdateParam gardenUpdateParamWithoutImageToDelete(Long gardenId) {
        return new GardenUpdateParam(
            gardenId,
            List.of(FIRST_GARDEN_IMAGE_URL, SECOND_GARDEN_IMAGE_URL),
            Collections.emptyList(),
            "별이네 텃밭",
            "100",
            "200",
            GardenStatus.ACTIVE,
            GardenType.PRIVATE,
            "000-000-0000",
            "인천광역시 서구 만수동 200",
            LATITUDE,
            LONGITUDE,
            new GardenUpdateParam.GardenFacility(
                true,
                true,
                true
            ),
            "화장실이 깨끗하고 흙이 좋아요",
            RECRUIT_START_DATE,
            RECRUIT_END_DATE,
            1L
        );
    }

    public static MyManagedGardenCreateParam myManagedGardenCreateParam(String expectedUrl) {
        MultipartFile multipartFile = new MockMultipartFile("test", expectedUrl.getBytes());

        return new MyManagedGardenCreateParam(
            multipartFile,
            1L,
            USE_START_DATE,
            USE_END_DATE,
            1L,
            "배추를 심었어요"
        );
    }

    public static MyManagedGardenCreateParam myManagedGardenCreateParamWithoutImage() {
        return new MyManagedGardenCreateParam(
            null,
            1L,
            USE_START_DATE,
            USE_END_DATE,
            1L,
            "배추를 심었어요"
        );
    }

    public static MyManagedGardenUpdateParam myManagedGardenUpdateParam(
        String expectedUrl,
        Long gardenId,
        Long myManagedGardenId) {
        MultipartFile multipartFile = new MockMultipartFile("test", expectedUrl.getBytes());

        return new MyManagedGardenUpdateParam(
            multipartFile,
            myManagedGardenId,
            gardenId,
            USE_START_DATE,
            USE_END_DATE,
            1L,
            "배추를 심었어요"
        );
    }

    public static MyManagedGardenUpdateParam myManagedGardenUpdateParamWithoutImage(
        Long gardenId,
        Long myManagedGardenId) {
        return new MyManagedGardenUpdateParam(
            null,
            myManagedGardenId,
            gardenId,
            USE_START_DATE,
            USE_END_DATE,
            1L,
            "배추를 심었어요"
        );
    }

    public static MyManagedGarden myManagedGarden(Long gardenId) {
        return MyManagedGarden.of(
            USE_START_DATE,
            USE_END_DATE,
            1L,
            "https://kr.object.ncloudstorage.com/every-garden/images/garden/background.jpg",
            gardenId
        );
    }

    public static GardenLike gardenLike(Garden garden, Long memberId) {
        return GardenLike.of(memberId, garden);
    }

}
