package com.mjutarzan.tarzan.domain.fraud.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RentRow {
    @JsonProperty("RCPT_YR")
    private String rcptYr;

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

    @JsonProperty("FLR")
    private Integer flr;

    @JsonProperty("CTRT_DAY")
    private String ctrtDay;

    @JsonProperty("RENT_SE")
    private String rentSe;

    @JsonProperty("RENT_AREA")
    private Double rentArea;

    @JsonProperty("GRFE")
    private String grfe;

    @JsonProperty("RTFE")
    private String rtfe;

    @JsonProperty("BLDG_NM")
    private String bldgNm;

    @JsonProperty("ARCH_YR")
    private String archYr;

    @JsonProperty("BLDG_USG")
    private String bldgUsg;

    @JsonProperty("CTRT_PRD")
    private String ctrtPrd;

    @JsonProperty("NEW_UPDT_YN")
    private String newUpdtYn;

    @JsonProperty("CTRT_UPDT_USE_YN")
    private String ctrtUpdtUseYn;

    @JsonProperty("BFR_GRFE")
    private String bfrGrfe;

    @JsonProperty("BFR_RTFE")
    private String bfrRtfe;
}
