package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.domain.OpenAPIGarden;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.service.dto.request.GardenUpdateFromOpenAPIParams;

import java.util.List;

public record OpenAPIAllGardenResults(
    List<OpenAPIAllGardenResult> results
) {
    public static OpenAPIAllGardenResults to(List<OpenAPIGarden> openAPIGardens) {
        return new OpenAPIAllGardenResults(
            openAPIGardens.stream()
                .map(OpenAPIAllGardenResult::toOpenAPIAllGardenResult)
                .toList()
        );
    }

    public GardenUpdateFromOpenAPIParams toGardenUpdateFromOpenAPIParams() {
        return new GardenUpdateFromOpenAPIParams(
            results.stream()
                .map(OpenAPIAllGardenResult::toGardenUpdateFromOpeAPIParam
                ).toList()
        );
    }

    public record OpenAPIAllGardenResult(
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
        private static final String PRICE_UNIT = "000";
        private static final Long OPEN_API_DEFAULT_WRITER_ID = -1L;
        private static final String OPENAPI_CONTACT = "신청하기 버튼을 눌러주시기 바랍니다.";
        private static final String GARDEN_DESCRIPTION = "텃밭을 가꿔봅시다.";


        public static OpenAPIAllGardenResult toOpenAPIAllGardenResult(OpenAPIGarden openAPIGarden) {

            return new OpenAPIAllGardenResult(
                openAPIGarden.getGardenName(),
                openAPIGarden.getPrice() + PRICE_UNIT,
                openAPIGarden.getGardenAreaSizeToSell(),
                GardenStatus.ACTIVE,
                openAPIGarden.getGardenType(),
                OPENAPI_CONTACT,
                openAPIGarden.getFullAddress(),
                Double.parseDouble(openAPIGarden.getLatitude()),
                Double.parseDouble(openAPIGarden.getLongitude()),
                openAPIGarden.getGardenFacilities(),
                GARDEN_DESCRIPTION,
                openAPIGarden.getRecruitmentPeriod(),
                openAPIGarden.getRecruitmentPeriod(),
                openAPIGarden.getUniqueHash(),
                OPEN_API_DEFAULT_WRITER_ID
            );
        }


        public GardenUpdateFromOpenAPIParams.GardenUpdateFromOpeAPIParam toGardenUpdateFromOpeAPIParam() {
            return new GardenUpdateFromOpenAPIParams.GardenUpdateFromOpeAPIParam(
                gardenName,
                price,
                size,
                gardenStatus,
                gardenType,
                contact,
                address,
                latitude,
                longitude,
                gardenFacilities,
                gardenDescription,
                recruitStartDate,
                recruitEndDate,
                resourceHashId,
                OPEN_API_DEFAULT_WRITER_ID
            );
        }

    }

}
