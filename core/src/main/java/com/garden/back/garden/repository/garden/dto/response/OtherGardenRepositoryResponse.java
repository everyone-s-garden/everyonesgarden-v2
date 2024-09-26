package com.garden.back.garden.repository.garden.dto.response;

import com.garden.back.garden.domain.vo.GardenStatus;

public interface OtherGardenRepositoryResponse {
    Long getGardenId();
    String getGardenName();
    String getPrice();
    String getImageUrl();
    GardenStatus getGardenStatus();
    Boolean getIsLiked();
}
