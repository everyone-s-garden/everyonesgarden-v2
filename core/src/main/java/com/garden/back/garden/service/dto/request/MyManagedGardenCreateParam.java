package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.MyManagedGardenCreateDomainRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record MyManagedGardenCreateParam(
    String myManagedGardenName,
    MultipartFile myManagedGardenImage,
    LocalDate createdAt,
    Long memberId,
    String description
) {
    public MyManagedGardenCreateDomainRequest to(
        String imageUrl
    ) {
        return new MyManagedGardenCreateDomainRequest(
            myManagedGardenName,
            imageUrl,
            createdAt,
            memberId,
            description
        );
    }

}
