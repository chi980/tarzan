package com.mjutarzan.tarzan.domain.board.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {

    private Long count;
    private List<BoardListItemResponseDto> list;
    private Boolean isNext;

    @Builder
    public BoardListResponseDto(Long count, List<BoardListItemResponseDto> list, Boolean isNext){
        this.count = count;
        this.list = list;
        this.isNext = isNext;

    }
}
