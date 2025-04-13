package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkChecklistResponseDto2 {

    @JsonProperty("idx")
    private Long id;

    @JsonProperty("label")
    private String label;

    @JsonProperty("value")
    private Boolean value;

    @Builder
    public BookmarkChecklistResponseDto2(Long id, String label, Boolean value){
        this.id = id;
        this.label = label;
        this.value = value;
    }
}
