package com.mjutarzan.tarzan.global.jwt.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokensRequestDto {
    @JsonProperty("refresh_token")
    private String refreshToken;
}
