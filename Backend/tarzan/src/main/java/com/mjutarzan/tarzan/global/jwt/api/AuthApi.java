package com.mjutarzan.tarzan.global.jwt.api;

import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.jwt.api.request.LogoutRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.request.ReIssueTokensRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.response.ReIssueTokensResponseDto;
import com.mjutarzan.tarzan.global.jwt.service.AuthService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<?> reissueTokens(@RequestBody ReIssueTokensRequestDto requestDto, @CookieValue(value = "X-refresh-token", required = false) String refreshToken) {
        log.info("email: {}", requestDto.getEmail());
        log.info("refresh token: {}", refreshToken);

        ReIssueTokensResponseDto response = authService.reissueAccessToken(requestDto, refreshToken);
        Cookie refreshTokenCookie = authService.reissueRefreshToken(requestDto);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())  // ✅ 쿠키 설정
                .body(BaseResponseDto.builder()
                    .success(true)
                    .data(response)
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
/*    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", deleteCookie.toString());
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }*/
}
