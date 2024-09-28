package com.mjutarzan.tarzan.domain.board.api.response;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String content;
    private BoardTag tag;
    private Long readCount;
    private Long writerId;
    private String writerNickname;

    @Builder
    public BoardListResponseDto(Long id, String title, String content, BoardTag tag, Long readCount, Long writerId, String writerNickname){
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.readCount = readCount;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
    }

    public BoardListResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.tag = board.getTag();
        this.readCount = board.getReadCount();
        this.writerId = board.getWriter().getId();
        this.writerNickname = board.getWriter().getNickname();
    }
}
