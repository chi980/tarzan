package com.mjutarzan.tarzan.domain.bookmark.entity;

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

    @Column(name = "bookmark_direction")
    private HouseDirection direction;

    @Column(name = "bookmark_is_option_table")
    private Boolean isOptionTable;

    @Column(name = "bookmark_is_option_fire")
    private Boolean isOptionFire;

    @Column(name = "bookmark_is_option_microwave")
    private Boolean isOptionMicrowave;

    @Column(name = "bookmark_is_option_refrigerator")
    private Boolean isOptionReffigerator;

    @Column(name="bookmark_is_option_washing_machine")
    private Boolean isOptionWashingMachine;

    @Column(name = "bookmark_is_option_sink")
    private Boolean isOptionSink;

    @Column(name = "bookmark_is_option_tv")
    private Boolean isOptionTv;

    @Column(name = "bookmark_is_option_air_conditioner")
    private Boolean isOptionAirConditioner;

    @Column(name = "bookmark_is_option_bed")
    private Boolean isOptionBed;

    @Column(name = "bookmark_is_option_closet")
    private Boolean isOptionCloset;

    @Column(name = "bookmark_is_option_shower_booth")
    private Boolean isOptionShowerBooth;

    @Column(name = "bookmark_is_option_bidet")
    private Boolean isOptionBidet;

    @Column(name = "bookmark_is_option_guard")
    private Boolean isOptionGuard;

    @Column(name = "bookmark_is_option_cctv")
    private Boolean isOptionCctv;

    @Column(name = "bookmark_is_option_unmanned_delivery_box")
    private Boolean isOptionUnmannedDeliveryBox;

    @Column(name = "bookmark_is_option_door_security")
    private Boolean isOptionDoorSecurity;

    @Column(name = "bookmark_is_option_intercom")
    private Boolean isOptionIntercom;

    @Column(name = "bookmark_is_option_card_key")
    private Boolean isOptionCardKey;

    @Column(name = "bookmark_is_option_fire_alarm")
    private Boolean isOptionFireAlarm;

    @Column(name = "bookmark_is_option_veranda")
    private Boolean isOptionVeranda;

    @Column(name = "bookmark_is_option_elevator")
    private Boolean isOptionElevator;

    @Column(name = "bookmark_is_option_wifi")
    private Boolean isOptionWifi;

    @Column(name = "bookmark_check_water1")
    private Boolean checkWater1;
    @Column(name = "bookmark_check_water2")
    private Boolean checkWater2;
    @Column(name = "bookmark_check_water3")
    private Boolean checkWater3;
    @Column(name = "bookmark_check_water4")
    private Boolean checkWater4;

    @Column(name = "bookmark_check_window1")
    private Boolean checkWindow1;
    @Column(name = "bookmark_check_window2")
    private Boolean checkWindow2;
    @Column(name = "bookmark_check_window3")
    private Boolean checkWindow3;
    @Column(name = "bookmark_check_window4")
    private Boolean checkWindow4;

    @Column(name = "bookmark_check_bathroom1")
    private Boolean checkBathroom1;
    @Column(name = "bookmark_check_bathroom2")
    private Boolean checkBathroom2;
    @Column(name = "bookmark_check_bathroom3")
    private Boolean checkBathroom3;
    @Column(name = "bookmark_check_bathroom4")
    private Boolean checkBathroom4;

    @Column(name = "bookmark_check_surroundings1")
    private Boolean checkSurroundings1;
    @Column(name = "bookmark_check_surroundings2")
    private Boolean checkSurroundings2;
    @Column(name = "bookmark_check_surroundings3")
    private Boolean checkSurroundings3;
    @Column(name = "bookmark_check_surroundings4")
    private Boolean checkSurroundings4;
    @Column(name = "bookmark_check_surroundings5")
    private Boolean checkSurroundings5;

    @Column(name = "bookmark_check_option1")
    private Boolean checkOption1;
    @Column(name = "bookmark_check_option2")
    private Boolean checkOption2;
    @Column(name = "bookmark_check_option3")
    private Boolean checkOption3;
    @Column(name = "bookmark_check_option4")
    private Boolean checkOption4;
    @Column(name = "bookmark_check_option5")
    private Boolean checkOption5;

    @Column(name = "bookmark_check_detail1")
    private Boolean checkDetail1;
    @Column(name = "bookmark_check_detail2")
    private Boolean checkDetail2;
    @Column(name = "bookmark_check_detail3")
    private Boolean checkDetail3;
    @Column(name = "bookmark_check_detail4")
    private Boolean checkDetail4;
    @Column(name = "bookmark_check_detail5")
    private Boolean checkDetail5;

    @Column(name = "bookmark_check_security1")
    private Boolean checkSecurity1;
    @Column(name = "bookmark_check_security2")
    private Boolean checkSecurity2;
    @Column(name = "bookmark_check_security3")
    private Boolean checkSecurity3;
    @Column(name = "bookmark_check_security4")
    private Boolean checkSecurity4;

    @Column(name = "bookmark_check_etc1")
    private Boolean checkEtc1;
    @Column(name = "bookmark_check_etc2")
    private Boolean checkEtc2;
    @Column(name = "bookmark_check_etc3")
    private Boolean checkEtc3;
    @Column(name = "bookmark_check_etc4")
    private Boolean checkEtc4;

    @Builder
    public Bookmark(
            House house, User user, LeaseType leaseType, Integer rent, Integer deposit, Integer commisionFee,
            Integer managementFee, String realEstate, String realEstatePhoneNumber, Boolean canAnimal, Integer parkingLotCoverage,
            Integer roomCnt, Integer bathRoomCnt, LocalDate availableMoveInDate, Integer floor, HouseDirection direction,
            Boolean isOptionTable, Boolean isOptionFire, Boolean isOptionMicrowave, Boolean isOptionReffigerator,
            Boolean isOptionWashingMachine, Boolean isOptionSink, Boolean isOptionTv, Boolean isOptionAirConditioner,
            Boolean isOptionBed, Boolean isOptionCloset, Boolean isOptionShowerBooth, Boolean isOptionBidet, Boolean isOptionGuard,
            Boolean isOptionCctv, Boolean isOptionUnmannedDeliveryBox, Boolean isOptionDoorSecurity, Boolean isOptionIntercom,
            Boolean isOptionCardKey, Boolean isOptionFireAlarm, Boolean isOptionVeranda, Boolean isOptionElevator, Boolean isOptionWifi,
            Boolean checkWater1, Boolean checkWater2, Boolean checkWater3, Boolean checkWater4, Boolean checkWindow1,
            Boolean checkWindow2, Boolean checkWindow3, Boolean checkWindow4, Boolean checkBathroom1, Boolean checkBathroom2,
            Boolean checkBathroom3, Boolean checkBathroom4, Boolean checkSurroundings1, Boolean checkSurroundings2,
            Boolean checkSurroundings3, Boolean checkSurroundings4, Boolean checkSurroundings5, Boolean checkOption1,
            Boolean checkOption2, Boolean checkOption3, Boolean checkOption4, Boolean checkOption5, Boolean checkDetail1,
            Boolean checkDetail2, Boolean checkDetail3, Boolean checkDetail4, Boolean checkDetail5, Boolean checkSecurity1,
            Boolean checkSecurity2, Boolean checkSecurity3, Boolean checkSecurity4, Boolean checkEtc1, Boolean checkEtc2,
            Boolean checkEtc3, Boolean checkEtc4) {

        this.house = house;
        this.user = user;
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
        this.isOptionTable = isOptionTable;
        this.isOptionFire = isOptionFire;
        this.isOptionMicrowave = isOptionMicrowave;
        this.isOptionReffigerator = isOptionReffigerator;
        this.isOptionWashingMachine = isOptionWashingMachine;
        this.isOptionSink = isOptionSink;
        this.isOptionTv = isOptionTv;
        this.isOptionAirConditioner = isOptionAirConditioner;
        this.isOptionBed = isOptionBed;
        this.isOptionCloset = isOptionCloset;
        this.isOptionShowerBooth = isOptionShowerBooth;
        this.isOptionBidet = isOptionBidet;
        this.isOptionGuard = isOptionGuard;
        this.isOptionCctv = isOptionCctv;
        this.isOptionUnmannedDeliveryBox = isOptionUnmannedDeliveryBox;
        this.isOptionDoorSecurity = isOptionDoorSecurity;
        this.isOptionIntercom = isOptionIntercom;
        this.isOptionCardKey = isOptionCardKey;
        this.isOptionFireAlarm = isOptionFireAlarm;
        this.isOptionVeranda = isOptionVeranda;
        this.isOptionElevator = isOptionElevator;
        this.isOptionWifi = isOptionWifi;
        this.checkWater1 = checkWater1;
        this.checkWater2 = checkWater2;
        this.checkWater3 = checkWater3;
        this.checkWater4 = checkWater4;
        this.checkWindow1 = checkWindow1;
        this.checkWindow2 = checkWindow2;
        this.checkWindow3 = checkWindow3;
        this.checkWindow4 = checkWindow4;
        this.checkBathroom1 = checkBathroom1;
        this.checkBathroom2 = checkBathroom2;
        this.checkBathroom3 = checkBathroom3;
        this.checkBathroom4 = checkBathroom4;
        this.checkSurroundings1 = checkSurroundings1;
        this.checkSurroundings2 = checkSurroundings2;
        this.checkSurroundings3 = checkSurroundings3;
        this.checkSurroundings4 = checkSurroundings4;
        this.checkSurroundings5 = checkSurroundings5;
        this.checkOption1 = checkOption1;
        this.checkOption2 = checkOption2;
        this.checkOption3 = checkOption3;
        this.checkOption4 = checkOption4;
        this.checkOption5 = checkOption5;
        this.checkDetail1 = checkDetail1;
        this.checkDetail2 = checkDetail2;
        this.checkDetail3 = checkDetail3;
        this.checkDetail4 = checkDetail4;
        this.checkDetail5 = checkDetail5;
        this.checkSecurity1 = checkSecurity1;
        this.checkSecurity2 = checkSecurity2;
        this.checkSecurity3 = checkSecurity3;
        this.checkSecurity4 = checkSecurity4;
        this.checkEtc1 = checkEtc1;
        this.checkEtc2 = checkEtc2;
        this.checkEtc3 = checkEtc3;
        this.checkEtc4 = checkEtc4;

        this.house.addBookmark(this);
        this.user.addBookmark(this);
    }
}
