package com.mjutarzan.tarzan.domain.review.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {

    private Long count;
    private List<ReviewListItemResponseDto> list;
    private Boolean isNext;

    @Builder
    public ReviewListResponseDto(Long count, List<ReviewListItemResponseDto> list, Boolean isNext){
        this.count = count;
        this.list = list;
        this.isNext = isNext;
    }
}
