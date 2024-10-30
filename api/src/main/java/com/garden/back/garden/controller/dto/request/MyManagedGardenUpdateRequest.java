package com.garden.back.garden.controller.dto.request;

import com.garden.back.garden.controller.dto.request.validation.ValidDate;
import com.garden.back.garden.service.dto.request.MyManagedGardenUpdateParam;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public record MyManagedGardenUpdateRequest(
    @NotBlank(message = "텃밭 이름은 NULL이거나 빈값일 수 없습니다.")
    String myManagedGardenName,

    @ValidDate
    String createdAt,

    String description
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public MyManagedGardenUpdateParam toMyManagedGardenUpdateParam(
        Long myManagedGardenId,
        MultipartFile newImage,
        Long memberId
    ) {
        if (newImage == null ) {
            newImage = (MultipartFile) Collections.emptyList();
        }

        return new MyManagedGardenUpdateParam(
            myManagedGardenName,
            newImage,
            myManagedGardenId,
            LocalDate.parse(createdAt, DATE_FORMATTER),
            memberId,
            isNull(description)
        );

    }

    private static String isNull(String field) {
        return field == null ? "" : field;
    }

}
