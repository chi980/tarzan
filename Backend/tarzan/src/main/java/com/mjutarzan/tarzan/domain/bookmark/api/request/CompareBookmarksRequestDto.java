package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class CompareBookmarksRequestDto {

    private List<Long> bookmark_ids;

    @Builder
    public CompareBookmarksRequestDto(List<Long> bookmark_ids) {
        this.bookmark_ids = bookmark_ids;
    }
}
