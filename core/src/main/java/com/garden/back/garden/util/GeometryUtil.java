package com.garden.back.garden.util;

import com.garden.back.garden.service.dto.request.GardenByComplexesParam;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {

    private static final String LINESTRING_SQL = "'LINESTRING(%f %f, %f %f)',4326";
    private GeometryUtil() {
        throw new RuntimeException("유틸클래스 생성자를 통해서 객체를 생성할 수 없습니다.");
    }

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point createPoint(double latitude, double longitude) {
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

    public static String makeDiagonalByLineString(
            GardenByComplexesParam param
    ) {
        return String.format(
                LINESTRING_SQL,
                param.endLat(),
                param.endLong(),
                param.startLat(),
                param.startLong()
        );

    }
}
