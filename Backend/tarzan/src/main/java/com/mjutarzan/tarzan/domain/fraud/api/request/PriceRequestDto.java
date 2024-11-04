package com.mjutarzan.tarzan.domain.fraud.api.request;

import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PriceRequestDto {
    @NotBlank
    private SiGunGu gu; // 자치구
    @NotBlank
    private String dong;

    private String searchBy;
    private String search;

    @NotNull
    private Integer numOfRows;
    @Min(value = 1, message = "pageNo must be greater than or equal to 1")
    @NotNull
    private Integer pageNo;


    public PriceRequestDto(SiGunGu gu, String dong, String searchBy, String search, Integer pageNo) {
        this.gu = gu;
        this.dong = dong;
        this.searchBy = searchBy;
        this.search = search;
        this.numOfRows = 100;
        this.pageNo = pageNo;
    }
}
