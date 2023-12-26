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
        Long memberId
) {
    public static MyManagedGardenUpdateDomainRequest to(
            MyManagedGardenUpdateParam param,
            String myManagedGardenImageUrl) {
        return new MyManagedGardenUpdateDomainRequest(
                myManagedGardenImageUrl,
                param.gardenId,
                param.useStartDate,
                param.useEndDate,
                param.memberId
        );
    }
}
