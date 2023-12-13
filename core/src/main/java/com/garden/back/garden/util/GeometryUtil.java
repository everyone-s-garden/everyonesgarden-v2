package com.garden.back.garden.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {

    private GeometryUtil() {
        throw new RuntimeException("유틸클래스 생성자를 통해서 객체를 생성할 수 없습니다.");
    }

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point createPoint(double latitude, double longitude) {
        return factory.createPoint(new Coordinate(longitude, latitude));
    }
}
