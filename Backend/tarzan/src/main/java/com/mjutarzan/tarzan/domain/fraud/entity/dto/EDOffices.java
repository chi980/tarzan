package com.mjutarzan.tarzan.domain.fraud.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EDOffices {
    private List<Field> field;
    private Integer pageNo;
    private String resultCode;
    private Integer totalCount;
    private Integer numOfRows;
    private String resultMsg;
}
