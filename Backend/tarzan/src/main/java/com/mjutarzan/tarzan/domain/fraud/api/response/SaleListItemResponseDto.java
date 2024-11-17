package com.mjutarzan.tarzan.domain.fraud.api.response;

import com.mjutarzan.tarzan.domain.fraud.entity.dto.SaleRow;
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
    public SaleListItemResponseDto(String gu, String dong, String streetNumber, String buildingName, String contractDate, Integer floor, Integer buildYear, BuildingUse buildingUse, Integer price, Double buildingArea, Double landArea, String realEstateLocation) {
        super(gu, dong, streetNumber, buildingName, contractDate, floor, buildYear, buildingUse);
        this.price = price;
        this.buildingArea = buildingArea;
        this.landArea = landArea;
        this.realEstateLocation = realEstateLocation;
    }

    public static SaleListItemResponseDto getInstance(SaleRow salerow){
        return SaleListItemResponseDto.builder()
                .gu(salerow.getCggNm())
                .dong(salerow.getStdgNm())
                .streetNumber(salerow.getMno()+"-"+salerow.getSno())
                .buildingName(salerow.getBldgNm())
                .contractDate(salerow.getCtrtDay())
                .floor(salerow.getFlr())
                .buildYear(salerow.getArchYr())
                .buildingUse(BuildingUse.fromKor(salerow.getBldgUsg()))
                .price(salerow.getThingAmt())
                .buildingArea(salerow.getArchArea())
                .landArea(salerow.getLandArea())
                .realEstateLocation(salerow.getOpbizRestagntSggNm())
                .build();
    }
}
