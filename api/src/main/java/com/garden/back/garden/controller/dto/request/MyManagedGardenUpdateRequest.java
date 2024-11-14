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
import java.util.List;
import java.util.Optional;

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
        return new MyManagedGardenUpdateParam(
            myManagedGardenName,
            getNewFile(newImage),
            myManagedGardenId,
            LocalDate.parse(createdAt, DATE_FORMATTER),
            memberId,
            isNull(description)
        );

    }

    private static Optional<MultipartFile> getNewFile(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(file);
    }

    private static String isNull(String field) {
        return field == null ? "" : field;
    }

}
