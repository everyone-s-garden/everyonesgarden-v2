package com.garden.back.weather;

import com.garden.back.weather.service.WeatherService;
import com.garden.back.weather.service.response.AllWeatherResponse;
import com.garden.back.weather.service.response.WeatherTimeApiResponse;
import com.garden.back.weather.service.response.WeekWeatherApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/weathers", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/all")
    public ResponseEntity<AllWeatherResponse> getAllWeather() {
        return ResponseEntity.ok(weatherService.getAllRegionsSkyStatusAndTemperature());
    }


    @GetMapping("/time")
    public ResponseEntity<WeatherTimeApiResponse> getWeatherByTime(@RequestParam("lat") String latitude,
                                                                   @RequestParam("long") String longitude) {
        return ResponseEntity.ok(weatherService.getFiveSkyStatusAndTemperatureAfterCurrentTime(longitude, latitude));
    }

    @GetMapping("/week")
    public ResponseEntity<WeekWeatherApiResponse> getWeekWeatherByDate(@RequestParam("lat") String latitude,
                                                                       @RequestParam("long") String longitude) {
        return ResponseEntity.ok(weatherService.getWeekSkyStatus(longitude, latitude));
    }

}