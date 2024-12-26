package com.app.weather.repository;

import com.app.weather.dto.WeatherDto;
import com.app.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {

    @Query("SELECT w from WeatherEntity w where w.location = ?1")
    List<WeatherEntity> getWeatherEntityByLocation(String location);
}
