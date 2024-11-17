package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class CompareBookmarksRequestDto {

    @JsonProperty("bookmark_ids")
    Set<Long> idList;

    @Builder
    public CompareBookmarksRequestDto(Set<Long> idList) {
        this.idList = idList;
    }
}
