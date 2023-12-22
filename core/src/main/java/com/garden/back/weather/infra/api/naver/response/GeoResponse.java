package com.garden.back.weather.infra.api.naver.response;

import java.util.List;

public record GeoResponse(List<Result> results) {

    public record Result(Region region) {

        public record Region(Area1 area1) {

            public record Area1(String name) {}
        }
    }
}