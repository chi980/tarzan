package com.mjutarzan.tarzan.domain.board.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequestDto {
    @JsonProperty("comment_content")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @JsonProperty("comment_board_idx")
    @NotBlank(message = "게시물 idx는 필수입니다.")
    private Long boardIdx;
}
