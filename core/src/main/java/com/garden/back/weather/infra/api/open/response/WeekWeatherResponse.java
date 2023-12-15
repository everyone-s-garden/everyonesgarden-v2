package com.garden.back.weather.infra.api.open.response;

import java.util.List;

public record WeekWeatherResponse(Response response) {

    public record Response(Body body) {

        public record Body(Items items) {

            public record Items(List<WeatherItem> item) {

                public record WeatherItem(
                    String category,
                    String fcstDate,
                    String fcstTime,
                    String fcstValue
                ) {}
            }
        }
    }
}