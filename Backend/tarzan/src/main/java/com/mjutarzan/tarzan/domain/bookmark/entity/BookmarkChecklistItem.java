package com.mjutarzan.tarzan.domain.bookmark.entity;

import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "bookmark_checklist_item",
        indexes = {
                @Index(name = "idx_bookmark_checklist_item", columnList = "bookmark_checklist_bookmark")
        }
)
public class BookmarkChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_checklist_item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bookmark_checklist_item_type")
    private BookmarkChecklistType type;

    @Column(name = "bookmark_checklist_item_title")
    private String title;

    @Column(name = "bookmark_checklist_item_order")
    private Integer order;

    @Column(name = "bookmark_checklist_item_name")
    private String name;

    @Column(name = "bookmark_checklist_item_value")
    private Boolean value;

    @ManyToOne
    @JoinColumn(name = "bookmark_checklist_bookmark")
    private Bookmark bookmark;

    @Builder
    public BookmarkChecklistItem( BookmarkChecklistType type, String title, Integer order, String name, Boolean value, Bookmark bookmark) {
        this.type = type;
        this.title = title;
        this.order = order;
        this.name = name;
        this.value = value;

        this.bookmark = bookmark;
        this.bookmark.addBookmarkCheckListItem(this);
    }

}
