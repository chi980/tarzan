package com.mjutarzan.tarzan.domain.review.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.review.model.vo.LeaseType;
import com.mjutarzan.tarzan.domain.review.model.vo.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListItemResponseDto {

    @JsonProperty("review_img_url")
    private String imgUrl;

    @JsonProperty("review_score")
    private Integer score;

    @JsonProperty("review_lease_type")
    private LeaseType leaseType;

    @JsonProperty("review_deposit")
    private Integer deposit;

    @JsonProperty("review_management_fee")
    private Integer managementFee;

    @JsonProperty("review_residence_period")
    private Integer residencePeriod;

    @JsonProperty("review_floor")
    private Integer floor;

    @JsonProperty("review_advantage")
    private String advantage;

    @JsonProperty("review_advantage_tags")
    private List<Tag> advantageTagList;

    @JsonProperty("review_disadvantage")
    private String disadvantage;

    @JsonProperty("review_disadvantage_tags")
    private List<Tag> disadvantageTagList;

    @JsonProperty("review_is_writer")
    private Boolean isWriter;

    @Builder
    public ReviewListItemResponseDto(String imgUrl, Integer score, LeaseType leaseType, Integer deposit,
                                     Integer managementFee, Integer residencePeriod, Integer floor,
                                     String advantage, List<Tag> advantageTagList, String disadvantage,
                                     List<Tag> disadvantageTagList, Boolean isWriter) {
        this.imgUrl = imgUrl;
        this.score = score;
        this.leaseType = leaseType;
        this.deposit = deposit;
        this.managementFee = managementFee;
        this.residencePeriod = residencePeriod;
        this.floor = floor;
        this.advantage = advantage;
        this.advantageTagList = advantageTagList;
        this.disadvantage = disadvantage;
        this.disadvantageTagList = disadvantageTagList;
        this.isWriter = isWriter;
    }
}
