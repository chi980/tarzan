package com.mjutarzan.tarzan.domain.house.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.house.model.dto.HouseIndexes;
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

    @JsonProperty("house_category")
    private String category;

    @JsonProperty("house_indexes")
    private HouseIndexes indexes;

    @JsonProperty("house_review_images")
    private List<String> reviewImageList;

    @JsonProperty("house_reviews")
    private List<ReviewListItemResponseDto> reviewList;

    @Builder
    public HouseItemResposeDto(String name, String address, Double latitude, Double longitude, String category,
                               HouseIndexes indexes,
                                List<String> reviewImageList, List<ReviewListItemResponseDto> reviewList) {
        this.name = name;
        this.address = address;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.indexes = indexes;
        this.reviewImageList = reviewImageList;
        this.reviewList = reviewList;
    }
}
