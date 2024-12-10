package com.app.weather.service;

import com.app.weather.dto.WeatherReturnDto;
import com.app.weather.entity.WeatherEntity;

public interface WeatherService {

    WeatherReturnDto callWeatherApi(String location);

    void saveRequest(WeatherEntity weatherEntity);
}
