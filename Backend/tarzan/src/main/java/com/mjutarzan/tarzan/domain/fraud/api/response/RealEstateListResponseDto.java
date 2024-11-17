package com.mjutarzan.tarzan.domain.fraud.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RealEstateListResponseDto {

    private Integer count;
    private List<RealEstateListItemResponseDto> list;
    private boolean isNext;

    @Builder
    public RealEstateListResponseDto(Integer count, List<RealEstateListItemResponseDto> list, boolean isNext) {
        this.count = count;
        this.list = list;
        this.isNext = isNext;
    }
}
