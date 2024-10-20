package com.mjutarzan.tarzan.domain.bookmark.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CompareBookmarkResponseDto {

    private List<CompareBookmarkDetailResponseDto> list;

    @Builder
    public CompareBookmarkResponseDto(List<CompareBookmarkDetailResponseDto> list) {
        this.list = list;
    }
}
