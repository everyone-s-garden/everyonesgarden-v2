package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.GardenByName;
import org.springframework.data.domain.Slice;

public record GardenByNameResults(
        Slice<GardenByNameResult> gardensByName
) {

    public static GardenByNameResults to(Slice<GardenByName> gardensByName) {
        return new GardenByNameResults(
                gardensByName.map(GardenByNameResult::to));
    }

    public record GardenByNameResult(
            Long gardenId,
            String gardenName,
            String address
    ) {
        public static GardenByNameResult to(GardenByName garden) {
            return new GardenByNameResult(
                    garden.getGardenId(),
                    garden.getGardenName(),
                    garden.getAddress()
            );
        }
    }

}
