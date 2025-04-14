package com.mjutarzan.tarzan.global.oauth2.dto;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    private final Map<String, Object> response;
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.response = (Map<String, Object>) attributes.get("response");
    }


    @Override
    public String getId() {
        return (String) response.get("id");
    }

    @Override
    public String getNickname() {
        return (String) response.get("nickname"); // 또는 email 원하면 변경

    }

    @Override
    public String getImageUrl() {
        return (String) response.get("profile_image");
    }
}
