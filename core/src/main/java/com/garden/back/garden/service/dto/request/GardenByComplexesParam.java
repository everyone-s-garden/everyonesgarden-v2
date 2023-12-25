package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import com.garden.back.global.GeometryUtil;

public record GardenByComplexesParam(
        String gardenType,
        Integer pageNumber,
        Double startLat,
        Double startLong,
        Double endLat,
        Double endLong
) {
    private static final int GARDEN_BY_PAGE_SIZE = 10;

    public static GardenByComplexesRepositoryRequest to(GardenByComplexesParam param) {
        return new GardenByComplexesRepositoryRequest(
                GardenType.isAllType(param.gardenType),
                GeometryUtil.makeDiagonalByLineString(param),
                GARDEN_BY_PAGE_SIZE,
                param.pageNumber()
        );
    }

}
