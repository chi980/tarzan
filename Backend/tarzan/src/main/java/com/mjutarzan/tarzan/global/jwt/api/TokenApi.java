package com.mjutarzan.tarzan.global.jwt.api;

import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.jwt.api.request.LogoutRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.request.ReIssueTokensRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.response.ReIssueTokensResponseDto;
import com.mjutarzan.tarzan.global.jwt.service.AuthService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TokenApi {

    private final AuthService authService;

    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    @PostMapping("/refresh")
    public ResponseEntity<?> reissueTokens(@CookieValue(value = REFRESH_TOKEN_SUBJECT, required = true) String refreshToken) {

        ResponseCookie accessTokenCookie = authService.reissueAccessToken(refreshToken);
        ResponseCookie refreshTokenCookie = authService.reissueRefreshToken(refreshToken);

        log.info("access token cooke: {}", accessTokenCookie.toString());
        log.info("refresh token cooke: {}", refreshTokenCookie.toString());


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())  // ✅ 쿠키 설정
                .body(BaseResponseDto.builder()
                    .success(true)
                    .build()
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto requestDto) {

        log.info("로그아웃되었습니다.");

        authService.logout(requestDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .build());
    }

}
