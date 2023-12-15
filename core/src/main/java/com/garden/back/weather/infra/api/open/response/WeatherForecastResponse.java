package com.garden.back.weather.infra.api.open.response;

import java.util.List;

public record WeatherForecastResponse(Response response) {

    public record Response(Body body) {


        public record Body(
            Items items,
            int pageNo,
            int numOfRows,
            int totalCount
        ) {

            public record Items(List<Item> item) {

                public record Item(
                    String baseTime,
                    String category,
                    int nx,
                    int ny,
                    String obsrValue
                ) {}
            }
        }
    }
}