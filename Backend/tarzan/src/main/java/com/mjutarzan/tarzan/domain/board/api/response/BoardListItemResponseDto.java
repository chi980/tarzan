package com.mjutarzan.tarzan.domain.board.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListItemResponseDto {
    @JsonProperty("board_id")
    private Long id;
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
    @JsonProperty("board_comment_count")
    private Long commentCount;
    @JsonProperty("board_is_writer")
    private Boolean isWriter;

    @Builder
    public BoardListItemResponseDto(Long id, String title, String content, BoardTag tag, Long readCount, Long writerId, String writerNickname, LocalDateTime createdAt, Long commentCount, Boolean isWriter){
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.readCount = readCount;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.createdAt = createdAt;
        this.commentCount = commentCount;
        this.isWriter = isWriter;
    }

    public BoardListItemResponseDto(Board board, Boolean isWriter){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag();
        this.readCount = board.getReadCount();
        this.writerId = board.getWriter().getId();
        this.writerNickname = board.getWriter().getNickname();
        this.commentCount = board.getCommentList().stream().count();
        this.createdAt = board.getCreatedAt();
        this.isWriter= isWriter;
    }
}
