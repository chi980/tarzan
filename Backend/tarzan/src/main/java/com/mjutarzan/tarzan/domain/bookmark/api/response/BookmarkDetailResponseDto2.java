package com.mjutarzan.tarzan.domain.bookmark.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseDirection;
import com.mjutarzan.tarzan.domain.review.model.vo.LeaseType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Getter
@NoArgsConstructor
public class BookmarkDetailResponseDto {

    @JsonProperty("bookmark_id")
    private Long id;

    @JsonProperty("bookmark_lease_type")
    private LeaseType leaseType;

    @JsonProperty("bookmark_rent")
    private Integer rent;

    @JsonProperty("bookmark_deposit")
    private Integer deposit;

    @JsonProperty("bookmark_commission_fee")
    private Integer commissionFee;

    @JsonProperty("bookmark_management_fee")
    private Integer managementFee;

    @JsonProperty("bookmark_real_estate")
    private String realEstate;

    @JsonProperty("bookmark_real_estate_phone_number")
    private String realEstatePhoneNumber;

    @JsonProperty("bookmark_can_animal")
    private Boolean canAnimal;

    @JsonProperty("bookmark_parking_lot_coverage")
    private Integer parkingLogCoverage;

    @JsonProperty("bookmark_room_cnt")
    private Integer roomCnt;

    @JsonProperty("bookmark_bath_room_cnt")
    private Integer bathRoomCnt;

    @JsonProperty("bookmark_available_move_in_date")
    private LocalDate availableMoveInDate;

    @JsonProperty("bookmark_floor")
    private Integer floor;

    @JsonProperty("bookmark_direction")
    private HouseDirection direction;

    @JsonProperty("bookmark_checklist")
    private Map<String, BookmarkChecklistResponseDto> checklist;

    @Builder
    public BookmarkDetailResponseDto(Long id, LeaseType leaseType, Integer rent, Integer deposit, Integer commissionFee, Integer managementFee, String realEstate, String realEstatePhoneNumber, Boolean canAnimal, Integer parkingLogCoverage, Integer roomCnt, Integer bathRoomCnt, LocalDate availableMoveInDate, Integer floor, HouseDirection direction, Map<String, BookmarkChecklistResponseDto> checklist) {
        this.id = id;
        this.leaseType = leaseType;
        this.rent = rent;
        this.deposit = deposit;
        this.commissionFee = commissionFee;
        this.managementFee = managementFee;
        this.realEstate = realEstate;
        this.realEstatePhoneNumber = realEstatePhoneNumber;
        this.canAnimal = canAnimal;
        this.parkingLogCoverage = parkingLogCoverage;
        this.roomCnt = roomCnt;
        this.bathRoomCnt = bathRoomCnt;
        this.availableMoveInDate = availableMoveInDate;
        this.floor = floor;
        this.direction = direction;
        this.checklist = checklist;
    }
}
