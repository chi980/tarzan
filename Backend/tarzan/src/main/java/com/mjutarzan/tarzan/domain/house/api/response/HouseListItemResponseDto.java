package com.mjutarzan.tarzan.domain.house.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HouseListItemResponseDto {

    @JsonProperty("house_name")
    private String name;

    @JsonProperty("house_category")
    private String category;

    @JsonProperty("house_address")
    private String address;

    @JsonProperty("house_latitude")
    private Double latitude;

    @JsonProperty("house_longitude")
    private Double longitude;

    @Builder
    public HouseListItemResponseDto(String name, String category, String address,
                                    Double latitude, Double longitude) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
