package com.mjutarzan.tarzan.domain.fraud.api.response;

import com.mjutarzan.tarzan.domain.fraud.entity.dto.RentRow;
import com.mjutarzan.tarzan.domain.fraud.entity.vo.BuildingUse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RentListItemResponseDto extends PriceListItemResponseDto{
    private String rentType; // 전월세 구분
    private Double rentArea; // 임대면적(㎡)
    private String security; //보증금
    private String rentFee; // 임대료
    private String bfrSecurity; // 종전보증금
    private String bfrRentFee; // 종전임대료

    @Builder
    public RentListItemResponseDto(String gu, String dong, String streetNumber, String buildingName, String contractDate, String contractDateRange, Integer floor,String buildYear, BuildingUse buildingUse, String rentType, Double rentArea, String security, String rentFee, String bfrSecurity, String bfrRentFee) {
        super(gu, dong, streetNumber, buildingName, contractDate, contractDateRange, floor, buildYear, buildingUse);
        this.rentType = rentType;
        this.rentArea = rentArea;
        this.security = security;
        this.rentFee = rentFee;
        this.bfrSecurity = bfrSecurity;
        this.bfrRentFee = bfrRentFee;
    }

    public static RentListItemResponseDto getInstance(RentRow rentRow) {
        return RentListItemResponseDto.builder()
                .gu(rentRow.getCggNm())
                .dong(rentRow.getStdgNm())
                .streetNumber(rentRow.getMno()+"-"+rentRow.getSno())
                .buildingName(rentRow.getBldgNm())
                .contractDate(rentRow.getCtrtDay())
                .contractDateRange(rentRow.getCtrtPrd())
                .floor(rentRow.getFlr())
                .buildYear(rentRow.getArchYr())
                .buildingUse(BuildingUse.fromKor(rentRow.getBldgUsg()))
                .rentType(rentRow.getRentSe())
                .rentArea(rentRow.getRentArea())
                .security(rentRow.getGrfe())
                .rentFee(rentRow.getRtfe())
                .bfrSecurity(rentRow.getBfrGrfe())
                .bfrRentFee(rentRow.getBfrRtfe())
                .build();
    }
}
