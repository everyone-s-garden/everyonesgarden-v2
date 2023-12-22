package com.garden.back.garden.repository.garden.dto.response;

public interface GardenLikeByMemberRepositoryResponse {
    Long getGardenId();

    String getSize();

    String getGardenName();

    String getPrice();

    String getImageUrl();

    String getGardenStatus();
}
