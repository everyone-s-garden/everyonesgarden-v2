package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.MyManagedGardenUpdateDomainRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

public record MyManagedGardenUpdateParam(
    String myManagedGardenName,
    MultipartFile myManagedGardenImage,
    Long myManagedGardenId,
    LocalDate createdAt,
    Long memberId,
    String description
) {

    public MyManagedGardenUpdateDomainRequest toMyManagedGardenUpdateDomainRequest(
        String myManagedGardenImageUrl) {
        return new MyManagedGardenUpdateDomainRequest(
            myManagedGardenName,
            myManagedGardenImageUrl,
            createdAt,
            memberId,
            description
        );
    }
}
