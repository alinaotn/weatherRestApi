package com.app.weather.dto;

import com.app.weather.entity.UserEntity;
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
    private Double temperature;
    private LocalTime timestamp;
    private UserEntity user;
}
