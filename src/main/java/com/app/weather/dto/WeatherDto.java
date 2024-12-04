package com.app.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    private String location;
    private String temperature;
    private LocalTime timestamp;
    private UserDto userDto;

}