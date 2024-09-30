package com.mjutarzan.tarzan.domain.board.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.board.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListItemResponseDto {
    @JsonProperty("comment_id")
    private Long id;
    @JsonProperty("comment_content")
    private String content;
    @JsonProperty("comment_writer_id")
    private Long writerId;
    @JsonProperty("comment_writer_nickname")
    private String writerNickname;
    @JsonProperty("comment_created_at")
    private LocalDateTime createdAt;
    @JsonProperty("comment_is_writer")
    private boolean isWriter;

    @Builder
    public CommentListItemResponseDto(Long id, String content, Long writerId, String writerNickname, LocalDateTime createdAt, boolean isWriter){
        this.id = id;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.createdAt = createdAt;
        this.isWriter = isWriter;
    }

    public CommentListItemResponseDto(Comment comment, boolean isWriter){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writerId = comment.getWriter().getId();
        this.writerNickname = comment.getWriter().getNickname();
        this.createdAt = comment.getCreatedAt();
        this.isWriter = isWriter;
    }
}
