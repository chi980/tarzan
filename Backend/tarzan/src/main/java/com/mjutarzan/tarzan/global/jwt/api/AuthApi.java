package com.mjutarzan.tarzan.global.jwt.api;

import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.jwt.api.request.RefreshTokensRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.response.RefreshTokensResponseDto;
import com.mjutarzan.tarzan.global.jwt.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthApi {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/refresh")
    public ResponseEntity<Object> refreshTokens(@RequestBody RefreshTokensRequestDto requestDto, @AuthenticationPrincipal UserDto userDto) {
        RefreshTokensResponseDto response = refreshTokenService.refreshTokens(requestDto, userDto.getEmail());

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .data(response)
                .build());
    }
}
