package com.app.weather.controller;

import com.app.weather.dto.WeatherByLocationRequestDto;
import com.app.weather.dto.WeatherReturnDto;
import com.app.weather.service.UserService;
import com.app.weather.service.WeatherService;
import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    public WeatherController(WeatherService weatherService, ModelMapper modelMapper, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @PostMapping("/weather")
    @ResponseBody
    public ResponseEntity<WeatherReturnDto> getWeatherByLocation(@RequestBody WeatherByLocationRequestDto weatherByLocationRequestDto, @RequestHeader("authorization") String authorizationHeader) {
        if (!userService.findApiToken(authorizationHeader.substring(7))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        };
        WeatherReturnDto weatherResponse =  weatherService.callWeatherApi(weatherByLocationRequestDto.getLocation());
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }

}
