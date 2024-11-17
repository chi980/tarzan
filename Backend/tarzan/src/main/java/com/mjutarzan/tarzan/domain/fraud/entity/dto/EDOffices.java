package com.mjutarzan.tarzan.domain.fraud.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EDOffices {
    @JsonProperty("field")
    private List<Field> field;
    @JsonProperty("pageNo")
    private Integer pageNo;
    @JsonProperty("resultCode")
    private String resultCode;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonProperty("numOfRows")
    private Integer numOfRows;
    @JsonProperty("resultMsg")
    private String resultMsg;
}
