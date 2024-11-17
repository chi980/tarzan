package com.mjutarzan.tarzan.domain.bookmark.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseDirection;
import com.mjutarzan.tarzan.domain.review.model.vo.LeaseType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@ToString
public class UpdateBookmarkRequestDto {

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
    private Map<Long, Boolean> checklist;
}
