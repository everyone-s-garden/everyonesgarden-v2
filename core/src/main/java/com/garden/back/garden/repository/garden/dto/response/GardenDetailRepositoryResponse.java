package com.garden.back.garden.repository.garden.dto.response;

import com.garden.back.garden.model.vo.GardenStatus;
import com.garden.back.garden.model.vo.GardenType;

import java.time.LocalDateTime;

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
    LocalDateTime getRecruitStartDate();
    LocalDateTime getRecruitEndDate();
    LocalDateTime getUseStartDate();
    LocalDateTime getUseEndDate();
    String getGardenDescription();
    String getImageUrl();
    boolean getIsToilet();
    boolean getIsWaterway();
    boolean getIsEquipment();
    boolean getIsLiked();
}
