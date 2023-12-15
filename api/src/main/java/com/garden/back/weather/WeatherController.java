package com.garden.back.weather;

import com.garden.back.weather.request.WeatherTimeApiRequest;
import com.garden.back.weather.request.WeekWeatherApiRequest;
import com.garden.back.weather.service.WeatherService;
import com.garden.back.weather.service.response.AllWeatherResponse;
import com.garden.back.weather.service.response.WeatherTimeApiResponse;
import com.garden.back.weather.service.response.WeekWeatherApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<WeatherTimeApiResponse> getWeatherByTime(@Valid @ModelAttribute WeatherTimeApiRequest request) {
        return ResponseEntity.ok(weatherService.getFiveSkyStatusAndTemperatureAfterCurrentTime(request.longitude(), request.latitude()));
    }

    @GetMapping("/week")
    public ResponseEntity<WeekWeatherApiResponse> getWeekWeatherByDate(@Valid @ModelAttribute WeekWeatherApiRequest request) {
        return ResponseEntity.ok(weatherService.getWeekSkyStatus(request.longitude(), request.latitude()));
    }

}