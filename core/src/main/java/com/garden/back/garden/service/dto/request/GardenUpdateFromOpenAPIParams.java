package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.Garden;
import com.garden.back.garden.domain.dto.GardenUpdateDomainRequest;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.global.GeometryUtil;

import java.util.List;

public record GardenUpdateFromOpenAPIParams(
    List<GardenUpdateFromOpeAPIParam> params
) {
    private static final Long OPEN_API_DEFAULT_WRITER_ID = -1L;
    public record GardenUpdateFromOpeAPIParam(
        String gardenName,
        String price,
        String size,
        GardenStatus gardenStatus,
        String gardenType,
        String contact,
        String address,
        Double latitude,
        Double longitude,
        String gardenFacilities,
        String gardenDescription,
        String recruitStartDate,
        String recruitEndDate,
        int resourceHashId,
        Long writerId
    ) {
        public Garden toEntity() {
            return Garden.createGarden(
                address,
                latitude,
                longitude,
                GeometryUtil.createPoint(latitude, longitude),
                GardenType.getOpenAPIGardenType(gardenType),
                gardenName,
                gardenStatus,
                price,
                contact,
                size,
                gardenDescription,
                recruitStartDate,
                recruitEndDate,
                gardenFacilities,
                writerId
            );
        }

        public GardenUpdateDomainRequest toGardenUpdateDomainRequest() {
            return new GardenUpdateDomainRequest(
                gardenName,
                price,
                size,
                gardenStatus,
                GardenType.getOpenAPIGardenType(gardenType),
                contact,
                address,
                latitude,
                longitude,
                gardenFacilities,
                gardenDescription,
                recruitStartDate,
                recruitEndDate,
                OPEN_API_DEFAULT_WRITER_ID
            );
        }
    }

}

