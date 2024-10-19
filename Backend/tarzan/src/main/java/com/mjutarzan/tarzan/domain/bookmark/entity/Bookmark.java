package com.mjutarzan.tarzan.domain.bookmark.entity;

import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseDirection;
import com.mjutarzan.tarzan.domain.house.entity.House;
import com.mjutarzan.tarzan.domain.review.model.vo.LeaseType;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Bookmark extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookmark_house")
    private House house;

    @ManyToOne
    @JoinColumn(name = "bookmark_user")
    private User user;

    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookmarkChecklistItem> checkListItemList = new ArrayList<>();

    @Column(name = "bookmark_status")
    private BookmarkStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "bookmark_lease_type")
    private LeaseType leaseType;

    @Column(name = "bookmark_rent")
    private Integer rent; // 월세

    @Column(name = "bookmark_deposit")
    private Integer deposit; // 보증금

    @Column(name = "bookmark_commision_fee")
    private Integer commisionFee; // 중개수수료

    @Column(name = "bookmark_management_fee")
    private Integer managementFee; // 관리비

    @Column(name = "bookmark_real_estate")
    private String realEstate;

    @Column(name="bookmark_real_estate_phone_number")
    private String realEstatePhoneNumber;

    @Column(name = "bookmark_can_animal")
    @ColumnDefault("false")
    private Boolean canAnimal;

    @Column(name = "bookmark_parking_lot_coverage")
    private Integer parkingLotCoverage;

    @Column(name = "bookmark_room_cnt")
    private Integer roomCnt;

    @Column(name = "bookmark_bath_room_cnt")
    private Integer bathRoomCnt;

    @Column(name = "bookmark_available_move_in_date")
    private LocalDate availableMoveInDate;

    @Column(name = "bookmark_floor")
    private Integer floor; // 층수

    @Enumerated(EnumType.STRING)
    @Column(name = "bookmark_direction")
    private HouseDirection direction;


    @Builder
    public Bookmark(House house, User user, BookmarkStatus status, LeaseType leaseType, Integer rent, Integer deposit, Integer commisionFee, Integer managementFee, String realEstate, String realEstatePhoneNumber, Boolean canAnimal, Integer parkingLotCoverage, Integer roomCnt, Integer bathRoomCnt, LocalDate availableMoveInDate, Integer floor, HouseDirection direction) {
        this.house = house;
        this.user = user;
        this.status = status;
        this.leaseType = leaseType;
        this.rent = rent;
        this.deposit = deposit;
        this.commisionFee = commisionFee;
        this.managementFee = managementFee;
        this.realEstate = realEstate;
        this.realEstatePhoneNumber = realEstatePhoneNumber;
        this.canAnimal = canAnimal;
        this.parkingLotCoverage = parkingLotCoverage;
        this.roomCnt = roomCnt;
        this.bathRoomCnt = bathRoomCnt;
        this.availableMoveInDate = availableMoveInDate;
        this.floor = floor;
        this.direction = direction;


        this.user.addBookmark(this);
        this.house.addBookmark(this);
    }

    public void updateStatus(BookmarkStatus status){
        this.status = status;
    }

    public void addBookmarkCheckListItem(BookmarkChecklistItem bookmarkChecklistItem){this.checkListItemList.add(bookmarkChecklistItem);}
}
