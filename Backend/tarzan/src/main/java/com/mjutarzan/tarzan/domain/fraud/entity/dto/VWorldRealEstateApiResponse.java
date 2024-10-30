package com.mjutarzan.tarzan.domain.fraud.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VWorldRealEstateApiResponse {
    @JsonProperty("EDOffices")
    private EDOffices edOffices;
}
