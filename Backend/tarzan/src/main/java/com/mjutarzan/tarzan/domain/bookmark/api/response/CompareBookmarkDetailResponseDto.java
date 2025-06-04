package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseIndexType;
import com.mjutarzan.tarzan.domain.map.model.vo.BuildingCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
public class CompareBookmarkDetailResponseDto {
    @JsonProperty("bookmark_id")
    private Long id;

    @JsonProperty("house_id")
    private Long houseId;

    @JsonProperty("house_name")
    private String name;

    @JsonProperty("house_address")
    private String address;

    @JsonProperty("house_category")
    private String category;

    @JsonProperty("house_score")
    private Integer score;

    @JsonProperty("house_costs")
    Map<String, String> costs;
    @JsonProperty("house_details")
    Map<String, String> details;

    @JsonProperty("house_indexes")
    Map<BuildingCategory, Double> indexes;

    @JsonProperty("house_checks")
    Map<BookmarkChecklistType, Long> checks;


    @JsonProperty("bookmark_created_at")
    private LocalDateTime bookmarkCreatedAt;

    @Builder
    public CompareBookmarkDetailResponseDto(Long id, Long houseId, String name, String address, String category, Integer score, Map<String, String> costs, Map<String, String> details, Map<BuildingCategory, Double> indexes, Map<BookmarkChecklistType, Long> checks, LocalDateTime bookmarkCreatedAt) {
        this.id = id;
        this.houseId = houseId;
        this.name = name;
        this.address = address;
        this.category = category;
        this.score = score;
        this.costs = costs;
        this.details = details;
        this.indexes = indexes;
        this.checks = checks;
        this.bookmarkCreatedAt = bookmarkCreatedAt;
    }
}
