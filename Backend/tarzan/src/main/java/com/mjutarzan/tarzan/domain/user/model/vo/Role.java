package com.mjutarzan.tarzan.domain.user.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// 스프링 시큐리티에서는 권한(Role) 코드에
//항상 "ROLE_" 접두사가 앞에 붙어야 하기 떄문에 "ROLE_"을 설정
public enum Role {

    GUEST("ROLE_GUEST"), USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String key;
}
