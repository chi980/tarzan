package com.mjutarzan.tarzan.domain.member.model.dto;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;

public interface OAuth2Response {

    //제공자 (Ex. naver, google, ...)
    String getProvider();

    // Role 리턴
    Role getRole();

    //제공자에서 발급해주는 아이디(번호)
    String getProviderId();
    //이메일
    String getEmail();
    //사용자 실명 (설정한 이름)
    String getName();


}
