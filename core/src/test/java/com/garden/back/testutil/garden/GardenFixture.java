package com.garden.back.testutil.garden;

import com.garden.back.garden.model.Garden;
import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;
import com.garden.back.garden.service.dto.GardenByNameParam;
import com.garden.back.garden.util.GeometryUtil;

public class GardenFixture {

    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;

    private GardenFixture() {
        throw new RuntimeException("생성자를 통해 객체를 만들 수 없습니다.");
    }

    public static Garden garden(){
        return Garden.of(
                "경기도 수원시 망포동",
                GeometryUtil.createPoint(LATITUDE, LONGITUDE),
                "진겸이네 텃밭농장",
                        GardenType.PUBLIC,
                        GardenStatus.ACTIVE);
    }

    public static GardenByNameParam gardenByNameParam() {
        return new GardenByNameParam(
                "진겸이네 텃밭농장",
                0
        );
    }
}
