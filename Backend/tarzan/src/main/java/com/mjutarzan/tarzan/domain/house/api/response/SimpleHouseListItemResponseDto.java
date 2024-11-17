package com.mjutarzan.tarzan.domain.house.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleHouseListItemResponseDto {

    @JsonProperty("house_id")
    private Long id;
    @JsonProperty("house_latitude")
    private Double latitude;
    @JsonProperty("house_longitude")
    private Double longitude;

    @Builder
    public SimpleHouseListItemResponseDto(Long id, Double latitude, Double longitude){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
