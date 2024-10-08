package com.mjutarzan.tarzan.domain.board.entity;

import com.mjutarzan.tarzan.domain.board.model.vo.BoardTag;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
//null인 값은 자동으로 insert 쿼리에 포함시키지 않음
@DynamicInsert
public class Board extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title")
    private String title;

    @Lob
    @Column(name = "board_content", columnDefinition = "TEXT") // 또는 CLOB
    private String content;

    @Column(name = "board_tag")
    @Enumerated(EnumType.STRING)
    private BoardTag tag;

    @Column(name = "board_read_count")
    @ColumnDefault("0")
    private Long readCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_gu")
    private SiGunGu gu;

    @ManyToOne
    @JoinColumn(name = "board_writer")
    private User writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Board(String title, String content, BoardTag tag, Long readCount, SiGunGu gu, User writer) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.readCount = readCount;
        this.gu = gu;
        this.writer = writer;
        this.writer.addBoard(this);
    }

    public void update(String title, String content, BoardTag tag){
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
    }
}
