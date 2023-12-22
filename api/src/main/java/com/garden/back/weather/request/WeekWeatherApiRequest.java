package com.garden.back.weather.request;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

public record WeekWeatherApiRequest(
        @NotEmpty(message = "위도를 입력해 주세요.") @DecimalMin(value = "-90.0", message = "-90.0 보다 커야합니다.") @DecimalMax(value = "90.0", message = "90.0 보다 작아야 합니다.") String latitude,
        @NotEmpty(message = "경도를 입력해 주세요.") @DecimalMin(value = "-180.0", message = "-180.0보다 커야 합니다.") @DecimalMax(value = "180.0", message = "180보다 작아야 합니다.") String longitude
) {}