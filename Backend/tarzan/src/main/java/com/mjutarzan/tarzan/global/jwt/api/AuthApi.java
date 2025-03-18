package com.mjutarzan.tarzan.global.jwt.api;

import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.jwt.api.request.LogoutRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.request.ReIssueTokensRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.response.ReIssueTokensResponseDto;
import com.mjutarzan.tarzan.global.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<?> reIssueTokens(@RequestBody ReIssueTokensRequestDto requestDto) {
        ReIssueTokensResponseDto response = authService.reissueTokens(requestDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .data(null)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto requestDto) {

        authService.logout(requestDto);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .build());
    }
}
