package com.mjutarzan.tarzan.domain.board.entity;

import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@DynamicInsert
public class Comment  extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column(name = "comment_content", columnDefinition = "TEXT") // 또는 CLOB
    private String content;

    @ManyToOne
    @JoinColumn(name = "comment_writer")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "comment_board")
    private Board board;

    @Builder
    public Comment(String content, User writer, Board board) {
        this.content = content;
        this.writer = writer;
        this.board = board;

        this.writer.addComment(this);
        this.board.addComment(this);
    }

    public void update(String content) {
        this.content = content;
    }
}
