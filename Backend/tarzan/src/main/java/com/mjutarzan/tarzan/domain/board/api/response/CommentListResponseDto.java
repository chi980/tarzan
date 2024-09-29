package com.mjutarzan.tarzan.domain.board.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {
    private Long count;
    private List<CommentListItemResponseDto> list;

    @Builder
    public CommentListResponseDto(Long count, List<CommentListItemResponseDto> list){
        this.count = count;
        this.list = list;

    }
}
