package com.mjutarzan.tarzan.domain.fraud.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.fraud.entity.dto.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RealEstateListItemResponseDto {
    @JsonProperty("register-code")
    private String registerCode; // 법인 등록번호

    @JsonProperty("real-estate-name")
    private String realEstateName; // 사업자 상호

    @JsonProperty("real-estate-person-name")
    private String realEstatePersonName; // 중개업자명

    @JsonProperty("status-code")
    private String statusCode; // 상태구분코드

    @JsonProperty("status-name")
    private String statusName; // 상태구분명

    @JsonProperty("register-date")
    private String registerDate; // 보증설정시작일

    @JsonProperty("establish-begin-date")
    private String estbsBeginDe; // 보증설정종료일

    @JsonProperty("establish-end-date")
    private String estbsEndDe; // 데이터기준일자

    @JsonProperty("lot-number-address")
    private String lotNumberAddress; // 지번주소

    @JsonProperty("road-name-address")
    private String roadNameAddress; // 도로명주소

    @JsonProperty("road-name-code")
    private String roadNameCode; // 도로명주소코드



    public static RealEstateListItemResponseDto getInstance(Field field) {
        return RealEstateListItemResponseDto.builder()
                .registerCode(field.getJurirno())
                .realEstateName(field.getBsnmCmpnm())
                .realEstatePersonName(field.getBrkrNm())
                .statusCode(field.getSttusSeCode())
                .statusName(field.getSttusSeCodeNm())
                .registerDate(field.getRegistDe())
                .estbsBeginDe(field.getEstbsBeginDe())
                .estbsEndDe(field.getEstbsEndDe())
                .lotNumberAddress(field.getMnnmadr())
                .roadNameAddress(field.getRdnmadr())
                .roadNameCode(field.getRdnmadrcode())
                .build();
    }
}
