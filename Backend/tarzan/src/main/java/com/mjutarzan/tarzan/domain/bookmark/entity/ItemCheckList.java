package com.mjutarzan.tarzan.domain.bookmark.entity;

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
public class ItemCheckList extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_checklist_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    // 30일 전 체크리스트
    @Column(name = "before_30days_1")
    private Boolean before30days1;

    @Column(name = "before_30days_2")
    private Boolean before30days2;

    @Column(name = "before_30days_3")
    private Boolean before30days3;

    @Column(name = "before_30days_4")
    private Boolean before30days4;

    @Column(name = "before_30days_5")
    private Boolean before30days5;

    @Column(name = "before_30days_6")
    private Boolean before30days6;

    @Column(name = "before_30days_7")
    private Boolean before30days7;

    // 7일 전 체크리스트
    @Column(name = "before_7days_1")
    private Boolean before7days1;

    @Column(name = "before_7days_2")
    private Boolean before7days2;

    @Column(name = "before_7days_3")
    private Boolean before7days3;

    @Column(name = "before_7days_4")
    private Boolean before7days4;

    // 1일 전 체크리스트
    @Column(name = "before_1days_1")
    private Boolean before1days1;

    @Column(name = "before_1days_2")
    private Boolean before1days2;

    // 당일 체크리스트
    @Column(name = "ddays_1")
    private Boolean ddays1;

    @Column(name = "ddays_2")
    private Boolean ddays2;

    @Column(name = "ddays_3")
    private Boolean ddays3;

    @Column(name = "ddays_4")
    private Boolean ddays4;

    @Column(name = "ddays_5")
    private Boolean ddays5;

    @Column(name = "ddays_6")
    private Boolean ddays6;

    @Builder
    public ItemCheckList(User user, Boolean before30days1, Boolean before30days2, Boolean before30days3,
                         Boolean before30days4, Boolean before30days5, Boolean before30days6, Boolean before30days7,
                         Boolean before7days1, Boolean before7days2, Boolean before7days3, Boolean before7days4,
                         Boolean before1days1, Boolean before1days2, Boolean ddays1, Boolean ddays2,
                         Boolean ddays3, Boolean ddays4, Boolean ddays5, Boolean ddays6) {
        this.user = user;
        this.before30days1 = before30days1;
        this.before30days2 = before30days2;
        this.before30days3 = before30days3;
        this.before30days4 = before30days4;
        this.before30days5 = before30days5;
        this.before30days6 = before30days6;
        this.before30days7 = before30days7;
        this.before7days1 = before7days1;
        this.before7days2 = before7days2;
        this.before7days3 = before7days3;
        this.before7days4 = before7days4;
        this.before1days1 = before1days1;
        this.before1days2 = before1days2;
        this.ddays1 = ddays1;
        this.ddays2 = ddays2;
        this.ddays3 = ddays3;
        this.ddays4 = ddays4;
        this.ddays5 = ddays5;
        this.ddays6 = ddays6;
    }

}
