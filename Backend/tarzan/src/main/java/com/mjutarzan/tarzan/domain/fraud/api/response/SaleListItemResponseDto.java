package com.mjutarzan.tarzan.domain.fraud.api.response;

import com.mjutarzan.tarzan.domain.fraud.entity.vo.BuildingUse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SaleListItemResponseDto extends PriceListItemResponseDto{
    private Integer price; // 물건금액
    private Double buildingArea; // 건물면적
    private Double landArea; // 토지면적
    private String realEstateLocation; // 신고한 개업공인중개사 시군구명

    @Builder
    public SaleListItemResponseDto(String gu, String dong, String streetNumber, String buildingName, String contractDate, String contractDateRange, Integer floor, String buildYear, BuildingUse buildingUse, Integer price, Double buildingArea, Double landArea, String realEstateLocation) {
        super(gu, dong, streetNumber, buildingName, contractDate, contractDateRange, floor, buildYear, buildingUse);
        this.price = price;
        this.buildingArea = buildingArea;
        this.landArea = landArea;
        this.realEstateLocation = realEstateLocation;
    }
}
