package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GardenAllResults(
        Slice<GardenAllResult> gardenAllResults
) {

    public static GardenAllResults of(Slice<GardenGetAll> gardens) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardens);
        return new GardenAllResults(
                gardens.map(
                        gardenGetAll -> GardenAllResult.to(gardenGetAll, gardenAndImages.get(gardenGetAll.getGardenId()))
                )
        );
    }

    private static Map<Long, List<String>> parseGardenAndImage(Slice<GardenGetAll> gardensGetAll) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardensGetAll.forEach(gardenGetAll ->
                gardenAndImages
                        .computeIfAbsent(gardenGetAll.getGardenId(), k -> new ArrayList<>())
                        .add(gardenGetAll.getImageUrl())
        );

        return gardenAndImages;
    }

    public record GardenAllResult(
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
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        public static GardenAllResult to(GardenGetAll garden, List<String> images) {
            return new GardenAllResult(
                    garden.getGardenId(),
                    garden.getAddress(),
                    garden.getLatitude(),
                    garden.getLongitude(),
                    garden.getGardenName(),
                    garden.getGardenType().name(),
                    garden.getLinkForRequest(),
                    garden.getPrice(),
                    garden.getContact(),
                    garden.getSize(),
                    garden.getGardenStatus().name(),
                    garden.getWriterId(),
                    garden.getRecruitStartDate().format(TIME_FORMATTER),
                    garden.getRecruitEndDate().format(TIME_FORMATTER),
                    garden.getUseStartDate().format(TIME_FORMATTER),
                    garden.getUseEndDate().format(TIME_FORMATTER),
                    garden.getGardenDescription(),
                    images,
                    new GardenFacility(
                            garden.getIsToilet(),
                            garden.getIsWaterway(),
                            garden.getIsEquipment()
                    ),
                    garden.getIsLiked()
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
