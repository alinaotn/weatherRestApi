package com.app.weather.controller;

import com.app.weather.dto.WeatherByLocationRequestDto;
import com.app.weather.dto.WeatherDto;
import com.app.weather.dto.WeatherReturnDto;
import com.app.weather.entity.UserEntity;
import com.app.weather.entity.WeatherEntity;
import com.app.weather.service.UserService;
import com.app.weather.service.WeatherService;
import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;


@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    public WeatherController(WeatherService weatherService, UserService userService, ModelMapper modelMapper) {
        this.weatherService = weatherService;
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @PostMapping("/weather")
    @ResponseBody
    public ResponseEntity<WeatherReturnDto> getWeatherByLocation(@RequestBody WeatherByLocationRequestDto weatherByLocationRequestDto, @RequestHeader("authorization") String authorizationHeader) {
        if (!userService.findApiToken(authorizationHeader.substring(7))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        };

        Long userId = userService.findUserIdByToken(authorizationHeader.substring(7));
        UserEntity user = userService.findUserByToken(authorizationHeader.substring(7));
        // TODO: check if request already exist
        // if yes and time is less then 30 seconds get values from cache
        // if no call weatherApi and save request

        WeatherReturnDto weatherResponse =  weatherService.callWeatherApi(weatherByLocationRequestDto.getLocation());
        WeatherDto request = new WeatherDto(weatherResponse.location.name, weatherResponse.getCurrent().getTemp_c(), LocalTime.now());
        logger.info(request.toString());
        WeatherEntity weatherRequest = modelMapper.map(request, WeatherEntity.class);
        weatherService.saveRequest(weatherRequest);
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }

}
