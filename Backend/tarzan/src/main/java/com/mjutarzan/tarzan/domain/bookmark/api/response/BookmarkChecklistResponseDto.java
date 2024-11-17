package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookmarkChecklistResponseDto {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("title")
    private String title;

    @JsonProperty("id_list")
    private List<Long> idList;

    @JsonProperty("order_list")
    private List<Integer> orderList;

    @JsonProperty("name_list")
    private List<String> nameList;

    @JsonProperty("value_list")
    private List<Boolean> valueList;

    @Builder
    public BookmarkChecklistResponseDto(Integer count, String title, List<Long> idList, List<Integer> orderList, List<String> nameList, List<Boolean> valueList) {
        this.count = count;
        this.title = title;
        this.idList = idList;
        this.orderList = orderList;
        this.nameList = nameList;
        this.valueList = valueList;
    }
}
