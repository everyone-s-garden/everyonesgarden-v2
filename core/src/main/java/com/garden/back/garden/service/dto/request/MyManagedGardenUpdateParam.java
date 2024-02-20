package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.MyManagedGardenUpdateDomainRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record MyManagedGardenUpdateParam(
    MultipartFile myManagedGardenImage,
    Long myManagedGardenId,
    Long gardenId,
    LocalDate useStartDate,
    LocalDate useEndDate,
    Long memberId,
    String description
) {
    public MyManagedGardenUpdateDomainRequest toMyManagedGardenUpdateDomainRequest(
        String myManagedGardenImageUrl) {
        return new MyManagedGardenUpdateDomainRequest(
            myManagedGardenImageUrl,
            gardenId,
            useStartDate,
            useEndDate,
            memberId,
            description
        );
    }
}
