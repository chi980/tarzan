package com.mjutarzan.tarzan.global.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseDto {

    private boolean success;
    private String message;
    private Object data;

    @Builder(builderClassName = "BaseResponseDtoBuilder")
    public BaseResponseDto(boolean success, String message, Object data){
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
