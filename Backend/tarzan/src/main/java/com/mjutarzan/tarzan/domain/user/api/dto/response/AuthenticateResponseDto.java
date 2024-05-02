package com.mjutarzan.tarzan.domain.user.api.dto.response;

import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.security.model.dto.TokenDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AuthenticateResponseDto extends BaseResponseDto {

    private TokenDto token;
    @Builder
    public AuthenticateResponseDto(boolean success, String message, Map<String, Object> data, TokenDto token){
        super(success, message, data);
        this.token = token;
    }
}
