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

    @Builder
    public ReviewListResponseDto(Long count, List<ReviewListItemResponseDto> list){
        this.count = count;
        this.list = list;
    }
}
