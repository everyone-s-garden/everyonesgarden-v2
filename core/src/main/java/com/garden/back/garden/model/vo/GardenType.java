package com.garden.back.garden.model.vo;

public enum GardenType {
    PUBLIC, PRIVATE;

    public void isValidGardenType(String gardenType){
        GardenType.valueOf(gardenType);
    }

}
