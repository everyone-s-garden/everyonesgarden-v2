package com.garden.back.weather.infra.api.open;

public record Region(
    int nx,
    int ny,
    String regionName,
    String regionId
) {
}