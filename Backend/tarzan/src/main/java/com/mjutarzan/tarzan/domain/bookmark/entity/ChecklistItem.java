package com.mjutarzan.tarzan.domain.bookmark.entity;

import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "checklist_item",
        indexes = {
                @Index(name = "idx_checklist_item_user_type", columnList = "checklist_user, checklist_item_type")
        }
)
public class ChecklistItem extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "checklist_item_type")
    private ChecklistType type;

    @Column(name = "checklist_item_title")
    private String title;

    @Column(name = "checklist_item_order")
    private Integer order;

    @Column(name = "checklist_item_name")
    private String name;

    @Column(name = "checklist_item_value")
    private Boolean value;

    @ManyToOne
    @JoinColumn(name = "checklist_user")
    private User user;

    @Builder
    public ChecklistItem(ChecklistType type, String title, Integer order, String name, Boolean value, User user){
        this.type = type;
        this.title = title;
        this.order = order;
        this.name = name;
        this.value = value;
        this.user = user;
        this.user.addCheckListItem(this);
    }
}
