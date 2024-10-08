package com.mjutarzan.tarzan.domain.house.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HouseListResponseDto {

    private Long count;
    private List<HouseListItemResponseDto> list;

    @Builder
    public HouseListResponseDto(Long count, List<HouseListItemResponseDto> list){
        this.count = count;
        this.list = list;
    }
}
