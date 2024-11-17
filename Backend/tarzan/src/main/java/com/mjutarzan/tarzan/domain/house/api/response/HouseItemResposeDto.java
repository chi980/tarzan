package com.mjutarzan.tarzan.domain.house.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.review.api.response.ReviewListItemResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HouseItemResposeDto {

    @JsonProperty("house_name")
    private String name;

    @JsonProperty("house_address")
    private String address;

    @JsonProperty("house_latitude")
    private Double latitude;

    @JsonProperty("house_longitude")
    private Double longitude;

    @JsonProperty("house_index_amenity")
    private Integer indexAmenity;

    @JsonProperty("house_index_clinic")
    private Integer indexClinic;

    @JsonProperty("house_index_security")
    private Integer indexSecurity;

    @JsonProperty("house_index_shopping")
    private Integer indexShopping;

    @JsonProperty("house_index_transportation")
    private Integer indexTransportation;

    @JsonProperty("house_review_images")
    private List<String> reviewImageList;

    @JsonProperty("house_reviews")
    private List<ReviewListItemResponseDto> reviewList;

    @Builder
    public HouseItemResposeDto(String name, String address, Double latitude, Double longitude,
                                Integer indexAmenity, Integer indexClinic, Integer indexSecurity,
                                Integer indexShopping, Integer indexTransportation,
                                List<String> reviewImageList, List<ReviewListItemResponseDto> reviewList) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.indexAmenity = indexAmenity;
        this.indexClinic = indexClinic;
        this.indexSecurity = indexSecurity;
        this.indexShopping = indexShopping;
        this.indexTransportation = indexTransportation;
        this.reviewImageList = reviewImageList;
        this.reviewList = reviewList;
    }
}
