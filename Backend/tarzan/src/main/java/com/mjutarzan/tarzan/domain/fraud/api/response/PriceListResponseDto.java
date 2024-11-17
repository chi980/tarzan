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
    private boolean isNext;

    @Builder
    public PriceListResponseDto(Integer count, List<? extends PriceListItemResponseDto> list, boolean isNext) {
        this.count = count;
        this.list = list;
        this.isNext = isNext;
    }
}
