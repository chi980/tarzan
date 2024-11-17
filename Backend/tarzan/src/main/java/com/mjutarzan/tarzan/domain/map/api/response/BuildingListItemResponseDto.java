package com.mjutarzan.tarzan.domain.map.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuildingListItemResponseDto {

    @JsonProperty("building_name")
    private String name;
    @JsonProperty("building_category")
    private String category;
    @JsonProperty("building_address")
    private String address;
    @JsonProperty("building_latitude")
    private Double latitude;
    @JsonProperty("building_longitude")
    private Double longitude;
    @JsonProperty("building_type")
    private BuildingType type;

    @Builder
    public BuildingListItemResponseDto(String name, String category, String address, Double latitude, Double longitude, BuildingType type){
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }


}
