package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.controller.dto.request.validation.ValidDate;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;
import com.garden.back.garden.service.dto.request.GardenUpdateParam;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import net.jqwik.api.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public record GardenUpdateRequest(
        List<String> remainGardenImageUrls,

        @NotBlank
        String gardenName,

        @NotBlank
        String price,

        @NotBlank
        String size,

        @NotBlank
        @EnumValue(enumClass = GardenStatus.class)
        String gardenStatus,

        @NotBlank
        @EnumValue(enumClass = GardenType.class)
        String gardenType,

        String linkForRequest,
        String contact,
        @NotBlank

        String address,

        @NotBlank
        @DecimalMin(value = "-90.0", message = "위도는 -90.0 보다 크거나 같아야 한다.")
        @DecimalMax(value = "90.0", message = "위도는 90.0보다 같거나 작아야 한다.")
        Double latitude,

        @NotBlank
        @DecimalMin(value = "-180.0", message = "경도는 -180.0 보다 크거나 같아야 한다.")
        @DecimalMax(value = "180.0", message = "경도는 180.0 보다 같거나 작아야 한다.")
        Double longitude,

        @NotNull
        Boolean isToilet,

        @NotNull
        Boolean isWaterway,

        @NotNull
        Boolean isEquipment,

        @NotBlank
        @Length(min = 10, message = "텃밭 설명은 최소 10글자입니다.")
        String gardenDescription,

        @ValidDate
        String recruitStartDate,

        @ValidDate
        String recruitEndDate,

        @ValidDate
        String useStartDate,

        @ValidDate
        String useEndDate
) {
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        public static GardenUpdateParam to(
                Long gardenId,
                List<MultipartFile> newGardenImages,
                GardenUpdateRequest gardenUpdateRequest,
                Long memberId
        ) {
                if (newGardenImages == null) {
                        newGardenImages = Collections.emptyList();
                }
                return new GardenUpdateParam(
                        gardenId,
                        gardenUpdateRequest.remainGardenImageUrls,
                        newGardenImages,
                        gardenUpdateRequest.gardenName(),
                        gardenUpdateRequest.price(),
                        gardenUpdateRequest.size(),
                        GardenStatus.valueOf(gardenUpdateRequest.gardenStatus),
                        GardenType.valueOf(gardenUpdateRequest.gardenType),
                        gardenUpdateRequest.linkForRequest,
                        gardenUpdateRequest.contact,
                        gardenUpdateRequest.address,
                        gardenUpdateRequest.latitude,
                        gardenUpdateRequest.longitude,
                        new GardenUpdateParam.GardenFacility(
                                gardenUpdateRequest.isToilet(),
                                gardenUpdateRequest.isWaterway(),
                                gardenUpdateRequest.isEquipment()
                        ),
                        gardenUpdateRequest.gardenDescription(),
                        LocalDate.parse(gardenUpdateRequest.recruitStartDate, DATE_TIME_FORMATTER),
                        LocalDate.parse(gardenUpdateRequest.recruitEndDate, DATE_TIME_FORMATTER),
                        LocalDate.parse(gardenUpdateRequest.useStartDate, DATE_TIME_FORMATTER),
                        LocalDate.parse(gardenUpdateRequest.useEndDate, DATE_TIME_FORMATTER),
                        memberId
                );
        }
}
