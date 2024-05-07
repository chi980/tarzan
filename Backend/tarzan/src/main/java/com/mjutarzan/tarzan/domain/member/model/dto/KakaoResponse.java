package com.mjutarzan.tarzan.domain.member.model.dto;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;

import java.util.Map;

public class KakaoResponse  implements OAuth2Response{
    /*
         System.out.println(attributes);
             {id=아이디값,
             connected_at=2022-02-22T15:50:21Z,
             properties={nickname=이름},
             kakao_account={
                 profile_nickname_needs_agreement=false,
                 profile={nickname=이름},
                 has_email=true,
                 email_needs_agreement=false,
                 is_email_valid=true,
                 is_email_verified=true,
                 email=이메일}
             }
         */
    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public Role getRole() {
        return Role.KAKAO;
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return kakaoAccount != null ? kakaoAccount.get("email").toString() : null;
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return properties != null ? properties.get("nickname").toString() : null;
    }
}
