package com.mjutarzan.tarzan.domain.user.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserRequestDto {
    private String email;
    private String provider;
    private String providerId;
}
