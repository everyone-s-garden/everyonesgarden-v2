package com.garden.back.garden.repository.garden.dto.response;

import java.time.LocalDate;

public interface RecentCreateGardenRepositoryResponse {

    Long getGardenId();

    String getGardenName();

    String getAddress();

    Double getLatitude();

    Double getLongitude();

    LocalDate getRecruitStartDate();

    LocalDate getRecruitEndDate();

    String getPrice();

    boolean getIsLiked();

    String getImageUrl();
}
