package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ChecklistRequestDto {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("value_list")
    private Map<Long, Boolean> valueList;
}
