package com.mjutarzan.tarzan.global.common.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BaseResponseDto {

    private boolean success;
    private String message;
    private Map<String, Object> data;

    @Builder
    public BaseResponseDto(boolean success, String message, Map<String, Object> data){
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
