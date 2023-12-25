package com.garden.back.garden.dto.request;

import com.garden.back.garden.dto.request.validation.ValidDate;
import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.service.dto.request.GardenCreateParam;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import net.jqwik.api.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record GardenCreateRequest(
        @NotBlank
        String gardenName,

        @NotBlank
        String price,

        @NotBlank
        String size,

        @NotBlank
        @EnumValue(enumClass = GardenStatus.class)
        String gardenStatus,
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

    public static GardenCreateParam to(
            List<MultipartFile> multipartFiles,
            GardenCreateRequest gardenCreateRequest,
            Long writerId
    ) {
        return new GardenCreateParam(
                multipartFiles,
                gardenCreateRequest.gardenName,
                gardenCreateRequest.price,
                gardenCreateRequest.size,
                GardenStatus.valueOf(gardenCreateRequest.gardenStatus),
                gardenCreateRequest.linkForRequest,
                gardenCreateRequest.contact,
                gardenCreateRequest.address,
                gardenCreateRequest.latitude,
                gardenCreateRequest.longitude,
                new GardenCreateParam.GardenFacility(
                        gardenCreateRequest.isToilet,
                        gardenCreateRequest.isWaterway,
                        gardenCreateRequest.isEquipment
                ),
                gardenCreateRequest.gardenDescription,
                LocalDate.parse(gardenCreateRequest.recruitStartDate, DATE_TIME_FORMATTER),
                LocalDate.parse(gardenCreateRequest.recruitEndDate, DATE_TIME_FORMATTER),
                LocalDate.parse(gardenCreateRequest.useStartDate, DATE_TIME_FORMATTER),
                LocalDate.parse(gardenCreateRequest.useEndDate, DATE_TIME_FORMATTER),
                writerId
        );

    }

    @AssertTrue(message = "모집 시작일은 모집 종료일보다 이후일 수 없습니다.")
    public boolean validateRecruitmentDate(String recruitStartDate, String recruitEndDate) {
        return isBeforeStartDateThanEndDate(recruitStartDate, recruitEndDate);
    }

    @AssertTrue(message = "사용 시작일은 사용 종료일보다 이후일 수 없습니다.")
    public boolean validateUseDate(String useStartDate, String useEndDate) {
        return isBeforeStartDateThanEndDate(useStartDate, useEndDate);
    }

    private boolean isBeforeStartDateThanEndDate(String startDate, String endDate) {
        return LocalDate.parse(startDate, DATE_TIME_FORMATTER).isBefore(LocalDate.parse(endDate, DATE_TIME_FORMATTER));
    }

}
