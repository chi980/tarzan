package com.mjutarzan.tarzan.global.jwt.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
public class ReIssueTokensResponseDto {
    @JsonProperty("access_token")
    private String accessToken;

    @Builder
    public ReIssueTokensResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
