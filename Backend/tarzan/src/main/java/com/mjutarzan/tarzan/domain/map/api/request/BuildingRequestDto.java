package com.mjutarzan.tarzan.domain.map.api.request;

import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuildingRequestDto {
    private BuildingType type;
    private Double latitude;
    private Double longitude;
    private Double radius;

    public BuildingRequestDto(BuildingType type, Double latitude, Double longitude, Double radius){
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}
