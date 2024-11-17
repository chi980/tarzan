package com.mjutarzan.tarzan.domain.board.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardDetailResponseDto {

    @JsonProperty("board_title")
    private String title;
    @JsonProperty("board_content")
    private String content;
    @JsonProperty("board_tag")
    private BoardTag tag;
    @JsonProperty("board_read_count")
    private Long readCount;
    @JsonProperty("board_writer_id")
    private Long writerId;
    @JsonProperty("board_writer_nickname")
    private String writerNickname;
    @JsonProperty("board_created_at")
    private LocalDateTime createdAt;
    @JsonProperty("board_is_writer")
    private Boolean isWriter;

    @Builder
    public BoardDetailResponseDto(String title, String content, BoardTag tag, Long readCount, Long writerId, String writerNickname, LocalDateTime createdAt, Boolean isWriter) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.readCount = readCount;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.createdAt = createdAt;
        this.isWriter = isWriter;
    }
}
