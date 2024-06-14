package com.garden.back.garden.service.dto.request;

import com.garden.back.garden.repository.mymanagedgarden.dto.MyManagedGardensGetRepositoryParam;
import org.springframework.data.domain.Pageable;

public record MyManagedGardenGetParam(
    Long memberId,
    Long nextManagedGardenId
) {
    public MyManagedGardensGetRepositoryParam toMyManagedGardensGetRepositoryParam(Pageable pageable ) {
        return new MyManagedGardensGetRepositoryParam(
            memberId,
            nextManagedGardenId,
            pageable
        );
    }

}
