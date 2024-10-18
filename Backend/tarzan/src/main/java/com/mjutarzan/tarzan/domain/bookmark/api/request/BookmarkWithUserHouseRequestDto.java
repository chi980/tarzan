package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookmarkWithUserHouseRequestDto {

    @JsonProperty("house_name")
    @NotBlank
    private String houseName;

    @JsonProperty("house_address")
    @NotBlank
    private String houseAddress;

    @JsonProperty("house_latitude")
    @NotNull
    private Double houseLatitude;

    @JsonProperty("house_longitude")
    @NotNull
    private Double houseLongitude;

    @JsonProperty("house_category")
    private String houseCategory;

}
