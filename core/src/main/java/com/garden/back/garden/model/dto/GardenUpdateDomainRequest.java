package com.garden.back.garden.model.dto;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;

import java.time.LocalDate;

public record GardenUpdateDomainRequest(
        String gardenName,
        String price,
        String size,
        GardenStatus gardenStatus,
        GardenType gardenType,
        String linkForRequest,
        String contact,
        String address,
        Double latitude,
        Double longitude,
        boolean isToilet,
        boolean isWaterway,
        boolean isEquipment,
        String gardenDescription,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        LocalDate useStartDate,
        LocalDate useEndDate,
        Long writerId
) {
}
