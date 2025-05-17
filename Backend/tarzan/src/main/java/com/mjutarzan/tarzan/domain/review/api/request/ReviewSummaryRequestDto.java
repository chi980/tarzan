package com.mjutarzan.tarzan.domain.review.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewSummaryRequestDto {

    @NotNull
    private Long houseId;


    @Builder
    public ReviewSummaryRequestDto(Long houseId) {
        this.houseId = houseId;
    }
}
