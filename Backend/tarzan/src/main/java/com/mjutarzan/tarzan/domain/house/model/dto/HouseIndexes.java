package com.mjutarzan.tarzan.domain.house.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseIndexes {

    @JsonProperty("house_index_amenity")
    private Double indexAmenity;

    @JsonProperty("house_index_clinic")
    private Double indexClinic;

    @JsonProperty("house_index_security")
    private Double indexSecurity;

    @JsonProperty("house_index_shopping")
    private Double indexShopping;

    @JsonProperty("house_index_transportation")
    private Double indexTransportation;
}
