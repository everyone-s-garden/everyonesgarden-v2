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
    String getLinkForRequest();
    String getPrice();
    String getContact();
    String getSize();
    GardenStatus getGardenStatus();
    Long getWriterId();
    LocalDate getRecruitStartDate();
    LocalDate getRecruitEndDate();
    LocalDate getUseStartDate();
    LocalDate getUseEndDate();
    String getGardenDescription();
    String getImageUrl();
    boolean getIsToilet();
    boolean getIsWaterway();
    boolean getIsEquipment();
    boolean getIsLiked();
}
