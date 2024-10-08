package com.mjutarzan.tarzan.global.advice;

import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import com.mjutarzan.tarzan.global.common.exception.RequiredParameterMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        // 401 Unauthorized 응답 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                BaseResponseDto.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(RequiredParameterMissingException.class)
    public ResponseEntity<Object> handleRequiredParameterMissingException(RequiredParameterMissingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponseDto.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        // 500 Internal Server Error 응답 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                BaseResponseDto.builder()
                        .success(false)
                        .message("서버 오류가 발생했습니다.")
                        .build());

    }


}