package com.garden.back.garden.repository.garden.dto;

import com.garden.back.garden.domain.vo.GardenStatus;
import com.garden.back.garden.domain.vo.GardenType;

public interface GardenGetAll {

    Long getGardenId();
    Double getLatitude();
    Double getLongitude();
    String getGardenName();
    GardenType getGardenType();
    String getPrice();
    String getSize();
    GardenStatus getGardenStatus();
    String getImageUrl();

}
