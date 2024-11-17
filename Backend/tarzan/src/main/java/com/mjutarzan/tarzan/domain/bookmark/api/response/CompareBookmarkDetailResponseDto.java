package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseIndexType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class CompareBookmarkDetailResponseDto {

    @JsonProperty("house_id")
    private Long id;

    @JsonProperty("house_name")
    private String name;

    @JsonProperty("house_address")
    private String address;

    @JsonProperty("house_category")
    private String category;

    @JsonProperty("house_score")
    private Integer score;

    @JsonProperty("house_indexes")
    Map<HouseIndexType, Long> indexes;

    @JsonProperty("house_checks")
    Map<BookmarkChecklistType, Long> checks;

    @Builder
    public CompareBookmarkDetailResponseDto(Long id, String name, String address, String category, Integer score, Map<HouseIndexType, Long> indexes, Map<BookmarkChecklistType, Long> checks) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.score = score;
        this.indexes = indexes;
        this.checks = checks;
    }
}
