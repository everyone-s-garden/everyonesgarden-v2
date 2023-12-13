package com.garden.back.garden.dto.response;

import com.garden.back.garden.dto.request.GardenGetAllRequest;
import com.garden.back.garden.service.dto.response.GardenAllResults;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GardenGetAllResponses(
        Slice<GardenGetAllResponse> gardenGetAllResponses
) {

    public static GardenGetAllResponses to(GardenAllResults gardenAllResults) {
        return new GardenGetAllResponses(
                gardenAllResults.gardenAllResults()
                        .map(GardenGetAllResponse::to)
        );
    }

    public record GardenGetAllResponse(
            Long gardenId,
            String address,
            Double latitude,
            Double longitude,
            String gardenName,
            String gardenType,
            String linkForRequest,
            String price,
            String contact,
            String size,
            String gardenStatus,
            Long writerId,
            String recruitStartDate,
            String recruitEndDate,
            String useStartDate,
            String useEndDate,
            String gardenDescription,
            List<String> images,
            GardenFacility gardenFacility,
            boolean isLiked
    ) {
        public static GardenGetAllResponse to(GardenAllResults.GardenAllResult result){
            return new GardenGetAllResponse(
                    result.gardenId(),
                    result.address(),
                    result.latitude(),
                    result.longitude(),
                    result.gardenName(),
                    result.gardenType(),
                    result.linkForRequest(),
                    result.price(),
                    result.contact(),
                    result.size(),
                    result.gardenStatus(),
                    result.writerId(),
                    result.recruitStartDate(),
                    result.recruitEndDate(),
                    result.useStartDate(),
                    result.useEndDate(),
                    result.gardenDescription(),
                    result.images(),
                    new GardenFacility(
                            result.gardenFacility().isToilet(),
                            result.gardenFacility().isWaterway(),
                            result.gardenFacility().isEquipment()
                    ),
                    result.isLiked()
            );
        }

        public record GardenFacility(
                boolean isToilet,
                boolean isWaterway,
                boolean isEquipment
        ) {

        }

    }
}
