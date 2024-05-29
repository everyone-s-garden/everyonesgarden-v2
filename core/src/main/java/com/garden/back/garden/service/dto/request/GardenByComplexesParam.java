package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.garden.repository.garden.dto.request.GardenByComplexesRepositoryRequest;
import com.garden.back.global.GeometryUtil;

public record GardenByComplexesParam(
    String gardenType,
    Double startLat,
    Double startLong,
    Double endLat,
    Double endLong
) {

public GardenByComplexesRepositoryRequest toGardenByComplexesRepositoryRequest() {
    return new GardenByComplexesRepositoryRequest(
        GardenType.isAllType(gardenType),
        GeometryUtil.makeDiagonalByLineString(this)
    );
}

}