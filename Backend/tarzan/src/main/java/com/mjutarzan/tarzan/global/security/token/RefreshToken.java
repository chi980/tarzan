package com.mjutarzan.tarzan.global.security.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
@Getter
@RequiredArgsConstructor
@RedisHash(value = "refreshToken")
public class RefreshToken {
    private static final long serialVersionUID = 1L;

    @Id
    private String refreshToken;
    private String memberUserName;

    @TimeToLive
    private Long expiration;

    public RefreshToken(String refreshToken, String memberUserName, Long expiration) {
        this.refreshToken = refreshToken;
        this.memberUserName = memberUserName;
        this.expiration = expiration;
    }
}
