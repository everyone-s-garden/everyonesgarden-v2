package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.controller.dto.request.validation.ValidDate;
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
import java.util.Collections;
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

    @Length(max = 100, message = "텃밭 설명은 최대 100글자입니다.")
    String gardenDescription,

    @ValidDate
    String recruitStartDate,

    @ValidDate
    String recruitEndDate
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public static GardenCreateParam to(
        List<MultipartFile> multipartFiles,
        GardenCreateRequest gardenCreateRequest,
        Long writerId
    ) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            throw new IllegalArgumentException("텃밭에 등록하는 사진은 빈 값일 수 없습니다. 최소 한 장이상의 사진을 등록해주세요");
        }

        return new GardenCreateParam(
            multipartFiles,
            gardenCreateRequest.gardenName,
            gardenCreateRequest.price,
            gardenCreateRequest.size,
            GardenStatus.valueOf(gardenCreateRequest.gardenStatus),
            isNull(gardenCreateRequest.contact),
            gardenCreateRequest.address,
            gardenCreateRequest.latitude,
            gardenCreateRequest.longitude,
            new GardenCreateParam.GardenFacility(
                gardenCreateRequest.isToilet,
                gardenCreateRequest.isWaterway,
                gardenCreateRequest.isEquipment
            ),
            isNull(gardenCreateRequest.gardenDescription),
            LocalDate.parse(gardenCreateRequest.recruitStartDate, DATE_TIME_FORMATTER),
            LocalDate.parse(gardenCreateRequest.recruitEndDate, DATE_TIME_FORMATTER),
            writerId
        );

    }

    @AssertTrue(message = "모집 시작일은 모집 종료일보다 이후일 수 없습니다.")
    public boolean validateRecruitmentDate(String recruitStartDate, String recruitEndDate) {
        return isBeforeStartDateThanEndDate(recruitStartDate, recruitEndDate);
    }

    private boolean isBeforeStartDateThanEndDate(String startDate, String endDate) {
        return LocalDate.parse(startDate, DATE_TIME_FORMATTER).isBefore(LocalDate.parse(endDate, DATE_TIME_FORMATTER));
    }

    private static String isNull(String field) {
        return field == null ? "" : field;
    }

}
