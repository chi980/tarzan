package com.mjutarzan.tarzan.global.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponseDto extends BaseResponseDto {

    public static BaseResponseDto fail(String message) {
        return BaseResponseDto.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static BaseResponseDto success(Object data) {
        return BaseResponseDto.builder()
                .success(true)
                .data(data)
                .build();
    }

    public static BaseResponseDto success(){
        return BaseResponseDto.builder()
                .success(true)
                .build();
    }
}
