package com.garden.back.weather.infra.api.open.response;

import java.util.List;

public record MidWeatherResponse(Response response) {

    public record Response(Body body) {

        public record Body(Items items) {

            public record Items(List<WeatherItem> item) {

                public record WeatherItem(
                    String wf3Am,
                    String wf4Am,
                    String wf5Am,
                    String wf6Am,
                    String wf7Am
                ) {}
            }
        }
    }
}