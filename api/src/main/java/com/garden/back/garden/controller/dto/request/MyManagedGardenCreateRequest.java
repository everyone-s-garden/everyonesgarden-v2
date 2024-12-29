package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.controller.dto.request.validation.ValidDate;
import com.garden.back.garden.service.dto.request.MyManagedGardenCreateParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public record MyManagedGardenCreateRequest(

    @NotBlank(message = "텃밭 이름은 NULL이거나 빈값일 수 없습니다.")
    String myManagedGardenName,
    @ValidDate
    String createdAt,

    String description
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public MyManagedGardenCreateParam of(
        MultipartFile gardenImage,
        Long memberId
    ) {

        return new MyManagedGardenCreateParam(
            myManagedGardenName,
            gardenImage,
            LocalDate.parse(createdAt, DATE_FORMATTER),
            memberId,
            isNull(description)
        );
    }

    @AssertTrue(message = "사용 시작일은 사용 종료일보다 이후일 수 없습니다.")
    public boolean validateUseDate(String useStartDate, String useEndDate) {
        return isBeforeStartDateThanEndDate(useStartDate, useEndDate);
    }

    private boolean isBeforeStartDateThanEndDate(String startDate, String endDate) {
        return LocalDate.parse(startDate, DATE_FORMATTER).isBefore(LocalDate.parse(endDate, DATE_FORMATTER));
    }

    private static String isNull(String field) {
        return field == null ? "" : field;
    }
}
