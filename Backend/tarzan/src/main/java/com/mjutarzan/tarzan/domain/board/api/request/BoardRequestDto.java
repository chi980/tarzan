package com.mjutarzan.tarzan.domain.board.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardRequestDto {
    @JsonProperty("board_title")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @JsonProperty("board_content")
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @JsonProperty("board_tag")
    @NotNull(message = "태그는 필수입니다.")  // NotBlank 대신 NotNull 사용
    private BoardTag tag;

    @JsonProperty("board_gu")
    @NotBlank(message = "구는 필수입니다.")
    private SiGunGu gu;
}
