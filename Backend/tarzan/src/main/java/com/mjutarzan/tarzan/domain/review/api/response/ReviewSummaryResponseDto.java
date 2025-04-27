package com.mjutarzan.tarzan.domain.review.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewSummaryResponseDto {

    @JsonProperty("review_score")
    private Double score;

    @JsonProperty("review_count")
    private Long count;

    @Builder
    public ReviewSummaryResponseDto(Double score, Long count) {
        this.score = score;
        this.count = count;
    }
}
