package com.mjutarzan.tarzan.domain.fraud.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TbLnOpendataSaleVResponse {
    @JsonProperty("list_total_count")
    private int listTotalCount;

    @JsonProperty("RESULT")
    private Result result;

    @JsonProperty("row")
    private List<SaleRow> rows;
}
