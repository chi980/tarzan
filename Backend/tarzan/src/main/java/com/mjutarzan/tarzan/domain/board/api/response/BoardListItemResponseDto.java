package com.mjutarzan.tarzan.domain.board.api.response;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardListItemResponseDto {
    private Long id;
    private String title;
    private String content;
    private BoardTag tag;
    private Long readCount;
    private Long writerId;
    private String writerNickname;
    private LocalDateTime createdAt;
    private long commentCount;
    private boolean isWriter;

    @Builder
    public BoardListItemResponseDto(Long id, String title, String content, BoardTag tag, Long readCount, Long writerId, String writerNickname, LocalDateTime createdAt, long commentCount, boolean isWriter){
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

    public BoardListItemResponseDto(Board board, boolean isWriter){
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
