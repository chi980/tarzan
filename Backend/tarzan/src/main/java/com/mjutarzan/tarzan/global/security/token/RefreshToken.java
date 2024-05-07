package com.mjutarzan.tarzan.global.security.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RequiredArgsConstructor
@RedisHash(value = "refreshToken")
public class RefreshToken {
    private static final long serialVersionUID = 1L;

    @Id
    private String refreshToken;

    @Indexed
    private String accessToken;

    private String memberUserName;

    @TimeToLive
    private Long expiration;

    public RefreshToken(String refreshToken, String accessToken, String memberUserName, Long expiration) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.memberUserName = memberUserName;
        this.expiration = expiration;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

