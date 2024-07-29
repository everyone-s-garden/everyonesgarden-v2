package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.OpenAPIGarden;

import java.util.List;

public record OpenAPIGardenUpdateParam(
    List<FarmInfo> farminfos
) {
    public record FarmInfo(
        String rowNum,
        String openAPIGardenId,
        String gardenType,
        String gardenName,
        String sidoCode,
        String sidoName,
        String sigunguCode,
        String sigunguName,
        String address,
        String totalGardenAreaSize,
        String gardenAreaSizeToSell,
        String homepage,
        String recruitmentPeriod,
        String gardenFacilities,
        String howToApply,
        String price,
        String latitude,
        String longitude,
        String registeredDate,
        String updatedDate
    ) {

        public OpenAPIGarden toOpenAPIGarden() {
            return OpenAPIGarden.of(
                rowNum,
                openAPIGardenId,
                gardenType,
                gardenName,
                sidoCode,
                sidoName,
                sigunguCode,
                sigunguName,
                address,
                totalGardenAreaSize,
                gardenAreaSizeToSell,
                homepage,
                gardenFacilities,
                registeredDate,
                updatedDate,
                recruitmentPeriod,
                howToApply,
                price,
                latitude,
                longitude
            );
        }

    }
}
