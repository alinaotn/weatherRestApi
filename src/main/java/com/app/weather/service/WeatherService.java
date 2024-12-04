package com.app.weather.service;

import com.app.weather.dto.WeatherReturnDto;

public interface WeatherService {

    WeatherReturnDto callWeatherApi(String location);
}
