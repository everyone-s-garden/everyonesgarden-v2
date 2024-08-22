package com.garden.back.garden.repository.garden.dto.response;

import java.time.LocalDate;

public interface RecentCreateGardenRepositoryResponse {

    Long getGardenId();

    String getGardenName();

    String getAddress();

    Double getLatitude();

    Double getLongitude();

    String getRecruitStartDate();

    String getRecruitEndDate();

    String getPrice();

    boolean getIsLiked();

    String getImageUrl();
}
