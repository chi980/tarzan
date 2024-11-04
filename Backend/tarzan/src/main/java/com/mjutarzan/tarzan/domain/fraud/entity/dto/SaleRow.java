package com.mjutarzan.tarzan.domain.fraud.entity.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleRow {

    @JsonProperty("RCPT_YR")
    private Integer rcptYr;

    @JsonProperty("CGG_CD")
    private String cggCd;

    @JsonProperty("CGG_NM")
    private String cggNm;

    @JsonProperty("STDG_CD")
    private String stdgCd;

    @JsonProperty("STDG_NM")
    private String stdgNm;

    @JsonProperty("LOTNO_SE")
    private String lotnoSe;

    @JsonProperty("LOTNO_SE_NM")
    private String lotnoSeNm;

    @JsonProperty("MNO")
    private String mno;

    @JsonProperty("SNO")
    private String sno;

    @JsonProperty("BLDG_NM")
    private String bldgNm;

    @JsonProperty("CTRT_DAY")
    private String ctrtDay;

    @JsonProperty("THING_AMT")
    private Integer thingAmt; // 물건금액

    @JsonProperty("ARCH_AREA")
    private Double archArea;

    @JsonProperty("LAND_AREA")
    private Double landArea;

    @JsonProperty("FLR")
    private Integer flr;

    @JsonProperty("RGHT_SE")
    private String rgthSe;

    @JsonProperty("RTRCN_DAY")
    private String rtrcnDay;

    @JsonProperty("ARCH_YR")
    private Integer archYr;

    @JsonProperty("BLDG_USG")
    private String bldgUsg;

    @JsonProperty("DCLR_SE")
    private String dclrSe;

    @JsonProperty("OPBIZ_RESTAGNT_SGG_NM")
    private String opbizRestagntSggNm;
}