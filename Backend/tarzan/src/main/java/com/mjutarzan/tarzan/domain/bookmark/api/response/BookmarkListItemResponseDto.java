package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookmarkListItemResponseDto {

    @JsonProperty("bookmark_id")
    private Long id;

    @JsonProperty("bookmark_house_id")
    private Long houseId;

    @JsonProperty("bookmark_house_name")
    private String houseName;

    @JsonProperty("bookmark_house_address")
    private String houseAddress;

    @JsonProperty("bookmark_house_category")
    private String houseCategory;

    @JsonProperty("bookmark_is_house_register")
    private Boolean isHouseRegister;

    @JsonProperty("bookmark_created_at")
    private LocalDateTime createdAt;

    @Builder
    public BookmarkListItemResponseDto(Long id, Long houseId, String houseName, String houseAddress,
                                       String houseCategory, Boolean isHouseRegister, LocalDateTime createdAt) {
        this.id = id;
        this.houseId = houseId;
        this.houseName = houseName;
        this.houseAddress = houseAddress;
        this.houseCategory = houseCategory;
        this.isHouseRegister = isHouseRegister;
        this.createdAt = createdAt;
    }
}
