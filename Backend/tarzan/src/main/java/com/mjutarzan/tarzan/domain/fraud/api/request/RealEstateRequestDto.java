package com.mjutarzan.tarzan.domain.fraud.api.request;

import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RealEstateRequestDto{

    @NotNull
    private SiGunGu gu;
    private String searchBy;
    private String search;
    @NotNull
    private Integer numOfRows;
    @Min(value = 1, message = "pageNo must be greater than or equal to 1")
    @NotNull
    private Integer pageNo;

    @Builder
    public RealEstateRequestDto(SiGunGu gu, String searchBy, String search, Integer pageNo) {
        this.gu = gu;
        this.searchBy = (searchBy != null) ? searchBy.trim() : null;
        this.search = (search != null) ? search.trim() : null;

        this.numOfRows = 10;
        this.pageNo = pageNo;
    }
}
