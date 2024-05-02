package com.mjutarzan.tarzan.domain.member.api.dto.response;

import com.mjutarzan.tarzan.global.security.model.dto.TokenDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateResponseDto{

    private TokenDto token;
    @Builder(builderClassName = "AuthenticateResponseDtoBuilder")
    public AuthenticateResponseDto(boolean success, String message, Object data, TokenDto token){
//        super(success, message, data);
        this.token = token;
    }
}
