package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.domain.dto.MyManagedGardenCreateDomainRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record MyManagedGardenCreateParam(
    MultipartFile myManagedGardenImage,
    Long gardenId,
    LocalDate useStartDate,
    LocalDate useEndDate,
    Long memberId,
    String description
) {
    public static MyManagedGardenCreateDomainRequest to(
        MyManagedGardenCreateParam param,
        String imageUrl
    ) {
        return new MyManagedGardenCreateDomainRequest(
            imageUrl,
            param.gardenId,
            param.useStartDate,
            param.useEndDate,
            param.memberId,
            param.description
        );
    }

}
