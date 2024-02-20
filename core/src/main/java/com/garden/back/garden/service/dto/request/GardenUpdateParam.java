package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.GardenUpdateDomainRequest;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record GardenUpdateParam (
        Long gardenId,
        List<String> remainGardenImageUrls,
        List<MultipartFile> newGardenImages,
        String gardenName,
        String price,
        String size,
        GardenStatus gardenStatus,
        GardenType gardenType,
        String contact,
        String address,
        Double latitude,
        Double longitude,
        GardenUpdateParam.GardenFacility gardenFacility,
        String gardenDescription,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        Long writerId

) {
    public GardenUpdateDomainRequest toGardenUpdateDomainRequest() {
        return new GardenUpdateDomainRequest(
                gardenName,
                price,
                size,
                gardenStatus,
                gardenType,
                contact,
                address,
                latitude,
                longitude,
                gardenFacility().isToilet,
                gardenFacility().isWaterway,
                gardenFacility().isEquipment,
                gardenDescription,
                recruitStartDate,
                recruitEndDate,
                writerId
        );
    }

    public record GardenFacility(
            boolean isToilet,
            boolean isWaterway,
            boolean isEquipment
    ) {

    }
}
