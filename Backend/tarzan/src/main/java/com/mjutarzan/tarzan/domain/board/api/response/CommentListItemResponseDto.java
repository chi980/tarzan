package com.mjutarzan.tarzan.domain.board.api.response;

import com.mjutarzan.tarzan.domain.board.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListItemResponseDto {
    private Long id;
    private String content;
    private Long writerId;
    private String writerNickname;
    private LocalDateTime createdAt;
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
