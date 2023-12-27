package com.garden.back.garden.dto.request;

import com.garden.back.garden.dto.request.validation.ValidDate;
import com.garden.back.garden.service.dto.request.MyManagedGardenUpdateParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record MyManagedGardenUpdateRequest(
        @Positive
        Long gardenId,

        @ValidDate
        String useStartDate,

        @ValidDate
        String useEndDate
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @AssertTrue(message = "사용 시작일은 사용 종료일보다 이후일 수 없습니다.")
    public boolean validateUseDate(String useStartDate, String useEndDate) {
        return isBeforeStartDateThanEndDate(useStartDate, useEndDate);
    }

    private boolean isBeforeStartDateThanEndDate(String startDate, String endDate) {
        return LocalDate.parse(startDate, DATE_FORMATTER).isBefore(LocalDate.parse(endDate, DATE_FORMATTER));
    }

    public static MyManagedGardenUpdateParam to(
            Long myManagedGardenId,
            MultipartFile newImage,
            MyManagedGardenUpdateRequest request,
            Long memberId
    ) {
        return new MyManagedGardenUpdateParam(
                newImage,
                myManagedGardenId,
                request.gardenId,
                LocalDate.parse(request.useStartDate, DATE_FORMATTER),
                LocalDate.parse(request.useEndDate, DATE_FORMATTER),
                memberId
        );

    }

}
