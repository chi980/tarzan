package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChecklistResponseDto {
    @JsonProperty("count")
    private Long count;

    @JsonProperty("name_list")
    private List<String> nameList;

    @JsonProperty("value_list")
    private List<Boolean> valueList;

    @Builder
    public ChecklistResponseDto(Long count, List<String> nameList, List<Boolean> valueList) {
        this.count = count;
        this.nameList = nameList;
        this.valueList = valueList;
    }
}
