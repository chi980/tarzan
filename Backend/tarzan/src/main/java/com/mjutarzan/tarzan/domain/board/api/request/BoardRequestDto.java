package com.mjutarzan.tarzan.domain.board.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardRequestDto {
    @JsonProperty("post_title")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String postTitle;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @JsonProperty("post_tag")
    @NotBlank(message = "태그는 필수입니다.")
    private String postTag;
}
