package com.mjutarzan.tarzan.global.security.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratedToken {

    private String accessToken;
    private String refreshToken;
}
