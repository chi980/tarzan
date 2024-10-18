package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ChecklistRequestDto {
    @JsonProperty("count")
    private Long count;

    @JsonProperty("name_list")
    private List<String> nameList;

    @JsonProperty("value_list")
    private List<Boolean> valueList;
}
