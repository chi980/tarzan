package com.mjutarzan.tarzan.domain.bookmark.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkListResponseDto {

    private Long count;
    private List<BookmarkListItemResponseDto> list;
    private Boolean isNext;

    @Builder
    public BookmarkListResponseDto(Long count, List<BookmarkListItemResponseDto> list, Boolean isNext){
        this.count = count;
        this.list = list;
        this.isNext = isNext;
    }
}
