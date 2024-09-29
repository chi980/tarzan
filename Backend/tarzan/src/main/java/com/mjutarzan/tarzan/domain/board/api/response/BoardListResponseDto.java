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

    @Builder
    public BoardListResponseDto(Long count, List<BoardListItemResponseDto> list){
        this.count = count;
        this.list = list;

    }
}
