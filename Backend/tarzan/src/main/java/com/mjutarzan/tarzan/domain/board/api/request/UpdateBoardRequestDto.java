package com.mjutarzan.tarzan.domain.board.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBoardRequestDto {
    @JsonProperty("board_title")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @JsonProperty("board_content")
    private String content;

    @JsonProperty("board_tag")
    private BoardTag tag;
}
