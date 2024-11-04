package com.mjutarzan.tarzan.domain.fraud.api.response;

import com.mjutarzan.tarzan.domain.fraud.entity.vo.BuildingUse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriceListItemResponseDto {
    private String gu;
    private String dong;
    private String streetNumber; // 지번(본번 - 부번)
    private String buildingName; // 건물명
    private String contractDate; // 계약일
    private Integer floor; // 층
    private Integer buildYear; // 건축년도
    private BuildingUse buildingUse; // 건물용도
}
