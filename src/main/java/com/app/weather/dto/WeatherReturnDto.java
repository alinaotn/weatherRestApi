package com.app.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.asm.MemberSubstitution;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherReturnDto {
    public Location location;
    public Current current;
}

