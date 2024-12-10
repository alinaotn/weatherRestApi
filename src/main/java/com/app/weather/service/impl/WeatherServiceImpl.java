package com.app.weather.service.impl;


import com.app.weather.dto.WeatherReturnDto;
import com.app.weather.entity.WeatherEntity;
import com.app.weather.repository.WeatherRepository;
import com.app.weather.service.WeatherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer your-token-here");
            return execution.execute(request, body);
        });
    }

    @Override
    @Cacheable(value = "weather")
    public WeatherReturnDto callWeatherApi(String location) {
        String url = "http://api.weatherapi.com/v1/current.json?key=4e39b52b4ca14841a2b123633242611&q=" + location;
        return restTemplate.getForObject(url, WeatherReturnDto.class);
    }

    @Override
    public void saveRequest(WeatherEntity weatherEntity) {
        weatherRepository.save(weatherEntity);
    }
}
