package com.mjutarzan.tarzan.domain.review.entity;

import com.mjutarzan.tarzan.domain.house.entity.House;
import com.mjutarzan.tarzan.domain.review.model.vo.LeaseType;
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
@Table(name = "review",  uniqueConstraints = @UniqueConstraint(columnNames = {"review_writer", "house_id"}))
@DynamicInsert
public class Review extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_lease_type")
    private LeaseType leaseType;

    @Column(name = "review_rent")
    private Integer rent; // 월세

    @Column(name = "review_deposit")
    private Integer deposit;

    @Column(name = "review_management_fee")
    private Integer managementFee;

    @Column(name = "review_residence_period")
    private Integer residencePeriod;

    @Column(name = "review_floor")
    private Integer floor;

    @Lob
    @Column(name = "review_advantage", columnDefinition = "TEXT") // 또는 CLOB
    private String advantage;

    @Lob
    @Column(name = "review_disadvantage", columnDefinition = "TEXT") // 또는 CLOB
    private String disadvantage;


    @Column(name = "review_option_none")
    private Boolean optionNone;

    @Column(name = "review_option_bug")
    private Boolean optionBug;

    @Column(name = "review_option_building_management")
    private Boolean optionBuildingManagement;

    @Column(name = "review_option_parking")
    private Boolean optionParking;

    @Column(name = "review_option_sound_proof")
    private Boolean optionSoundProof;

    @Column(name = "review_option_security")
    private Boolean optionSecurity;

    @Column(name = "review_option_trail")
    private Boolean optionTrail;

    @Column(name = "review_option_security_guard")
    private Boolean optionSecurityGuard;

    @Column(name = "review_option_ventilation")
    private Boolean optionVentilation;

    @Column(name = "review_option_flat")
    private Boolean optionFlat;

    @Column(name = "review_option_quite")
    private Boolean optionQuite;

    @Column(name = "review_option_prevent_heat")
    private Boolean optionPreventHeat;

    @Column(name = "review_option_pet")
    private Boolean optionPet;

    @Column(name = "review_option_elevator")
    private Boolean optionElevator;

    @Column(name = "review_option_mold")
    private Boolean optionMold;

    @ManyToOne
    @JoinColumn(name = "review_house", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "review_writer", nullable = false)
    private User writer;


    @Builder
    public Review(Long id, LeaseType leaseType, Integer rent, Integer deposit, Integer managementFee,
                  Integer residencePeriod, Integer floor, String advantage, String disadvantage,
                  Boolean optionNone, Boolean optionBug, Boolean optionBuildingManagement,
                  Boolean optionParking, Boolean optionSoundProof, Boolean optionSecurity,
                  Boolean optionTrail, Boolean optionSecurityGuard, Boolean optionVentilation,
                  Boolean optionFlat, Boolean optionQuite, Boolean optionPreventHeat,
                  Boolean optionPet, Boolean optionElevator, Boolean optionMold,
                  House house, User writer) {
        this.id = id;
        this.leaseType = leaseType;
        this.rent = rent;
        this.deposit = deposit;
        this.managementFee = managementFee;
        this.residencePeriod = residencePeriod;
        this.floor = floor;
        this.advantage = advantage;
        this.disadvantage = disadvantage;
        this.optionNone = optionNone;
        this.optionBug = optionBug;
        this.optionBuildingManagement = optionBuildingManagement;
        this.optionParking = optionParking;
        this.optionSoundProof = optionSoundProof;
        this.optionSecurity = optionSecurity;
        this.optionTrail = optionTrail;
        this.optionSecurityGuard = optionSecurityGuard;
        this.optionVentilation = optionVentilation;
        this.optionFlat = optionFlat;
        this.optionQuite = optionQuite;
        this.optionPreventHeat = optionPreventHeat;
        this.optionPet = optionPet;
        this.optionElevator = optionElevator;
        this.optionMold = optionMold;
        this.house = house;
        this.house.addReview(this);
        this.writer = writer;
        this.writer.addReview(this);
    }
}
