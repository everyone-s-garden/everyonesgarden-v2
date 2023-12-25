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
        String linkForRequest,
        String contact,
        String address,
        Double latitude,
        Double longitude,
        GardenUpdateParam.GardenFacility gardenFacility,
        String gardenDescription,
        LocalDate recruitStartDate,
        LocalDate recruitEndDate,
        LocalDate useStartDate,
        LocalDate useEndDate,
        Long writerId

) {
    public static GardenUpdateDomainRequest of(GardenUpdateParam param) {
        return new GardenUpdateDomainRequest(
                param.gardenName,
                param.price,
                param.size,
                param.gardenStatus,
                param.gardenType,
                param.linkForRequest,
                param.contact,
                param.address,
                param.latitude,
                param.longitude,
                param.gardenFacility().isToilet,
                param.gardenFacility().isWaterway,
                param.gardenFacility().isEquipment,
                param.gardenDescription,
                param.recruitStartDate,
                param.recruitEndDate,
                param.useStartDate,
                param.useEndDate,
                param.writerId
        );
    }

    public record GardenFacility(
            boolean isToilet,
            boolean isWaterway,
            boolean isEquipment
    ) {

    }
}
