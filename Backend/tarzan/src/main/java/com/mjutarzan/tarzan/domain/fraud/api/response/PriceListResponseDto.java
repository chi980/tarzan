package com.mjutarzan.tarzan.domain.fraud.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PriceListResponseDto {
    private Integer count;
    private List<? extends PriceListItemResponseDto> list;
    private Boolean isNext;

    @Builder
    public PriceListResponseDto(Integer count, List<? extends PriceListItemResponseDto> list, Boolean isNext) {
        this.count = count;
        this.list = list;
        this.isNext = isNext;
    }
}
