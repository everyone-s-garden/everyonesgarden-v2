package com.garden.back.garden.service.dto.request;


public record GardenDeleteEvent(Long gardenId) {
    public static GardenDeleteEvent of(Long gardenId) {
        return new GardenDeleteEvent(gardenId);
    }
}
