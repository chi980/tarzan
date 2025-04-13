package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@ToString
public class UpdateBookmarkChecklistRequestDto {
    @JsonProperty("bookmark_checklist")
    private Map<Long, Boolean> checklist = Collections.emptyMap();
}
