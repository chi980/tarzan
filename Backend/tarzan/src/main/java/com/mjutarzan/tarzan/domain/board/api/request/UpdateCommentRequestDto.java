package com.mjutarzan.tarzan.domain.board.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCommentRequestDto {
    @JsonProperty("comment_content")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

}
