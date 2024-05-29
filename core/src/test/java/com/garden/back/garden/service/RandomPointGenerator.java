package com.garden.back.garden.service;

import com.garden.back.garden.service.dto.request.GardenByComplexesWithScrollParam;
import com.garden.back.global.GeometryUtil;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPointGenerator {

    private RandomPointGenerator() {
        throw new RuntimeException("생성자를 통해서 객체를 생성할 수 없습니다.");
    }
    private static final int DEFAULT_COUNT = 5;

    public static List<Point> generateRandomPoint(GardenByComplexesWithScrollParam param) {
        List<Point> coordinates = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < DEFAULT_COUNT; i++) {
                double latitude = getRandomInRange(param.startLat(), param.endLat(), random);
                double longitude = getRandomInRange(param.startLong(), param.endLong(), random);
                coordinates.add(GeometryUtil.createPoint(latitude,longitude));
        }
        return coordinates;
    }

    private static double getRandomInRange(double min, double max, Random random) {
        return min + (max - min) * random.nextDouble();
    }


}
