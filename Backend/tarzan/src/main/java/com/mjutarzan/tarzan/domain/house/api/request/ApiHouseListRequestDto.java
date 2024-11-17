package com.mjutarzan.tarzan.domain.house.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiHouseListRequestDto {

    private Double latitude;
    private Double longitude;
    private Double radius;

    @Builder
    public ApiHouseListRequestDto(Double latitude, Double longitude, Double radius){
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}
