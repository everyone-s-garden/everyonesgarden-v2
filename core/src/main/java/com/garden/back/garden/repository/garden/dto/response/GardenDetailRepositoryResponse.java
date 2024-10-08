package com.garden.back.garden.repository.garden.dto.response;

import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;

import java.time.LocalDate;

public interface GardenDetailRepositoryResponse {
    Long getGardenId();
    String getAddress();
    Double getLatitude();
    Double getLongitude();
    String getGardenName();
    GardenType getGardenType();
    String getPrice();
    String getContact();
    String getSize();
    GardenStatus getGardenStatus();
    Long getWriterId();
    String getRecruitStartDate();
    String getRecruitEndDate();
    String getGardenDescription();
    String getImageUrl();
    String getGardenFacilities();
    Long getGardenLikeId();

    int getResourceHashId();
}
