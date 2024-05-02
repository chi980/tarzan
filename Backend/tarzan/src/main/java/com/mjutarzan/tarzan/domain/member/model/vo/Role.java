package com.mjutarzan.tarzan.domain.member.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    // 스프링 시큐리티에서는 권한 코드에 항성 ROLE_이 앞에 있어야만 한다
    USER("ROLE_USER", "USER"),
    KAKAO("ROLE_KAKAO", "KAKAO"),
    GOOGLE("ROLE_GOOGLE", "GOOGLE");

    private final String key;
    private final String title;
}
