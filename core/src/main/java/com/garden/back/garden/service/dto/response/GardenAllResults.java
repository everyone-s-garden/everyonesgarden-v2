package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.GardenGetAll;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GardenAllResults(
        List<GardenAllResult> gardenAllResults,
        boolean hasNext
) {
    public static GardenAllResults of(Slice<GardenGetAll> gardens) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardens);
        return new GardenAllResults(
                gardens.map(
                        gardenGetAll -> GardenAllResult.to(gardenGetAll, gardenAndImages.get(gardenGetAll.getGardenId()))
                ).toList(),
                gardens.hasNext()
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
            Double latitude,
            Double longitude,
            String gardenName,
            String gardenType,
            String price,
            String size,
            String gardenStatus,
            List<String> images
    ) {
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        public static GardenAllResult to(GardenGetAll garden, List<String> images) {
            return new GardenAllResult(
                    garden.getGardenId(),
                    garden.getLatitude(),
                    garden.getLongitude(),
                    garden.getGardenName(),
                    garden.getGardenType().name(),
                    garden.getPrice(),
                    garden.getSize(),
                    garden.getGardenStatus().name(),
                    images
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
