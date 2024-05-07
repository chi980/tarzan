package com.mjutarzan.tarzan.global.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponseDto {

    private boolean success;
    private String message;
    private Object data;

    @Builder
    public BaseResponseDto(boolean success, String message, Object data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
