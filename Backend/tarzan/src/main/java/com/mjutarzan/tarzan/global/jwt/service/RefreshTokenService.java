package com.mjutarzan.tarzan.global.jwt.service;

import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import com.mjutarzan.tarzan.global.jwt.api.request.RefreshTokensRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.response.RefreshTokensResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Transactional
public class RefreshTokenService {

    private final JwtService jwtService;

    // ✅ Refresh Token 생성
    public String createRefreshToken(String email) {
        String newRefreshToken = jwtService.generateRefreshToken();
        jwtService.updateRefreshToken(email, newRefreshToken);
        return newRefreshToken;
    }

    // ✅ Refresh Token 만료 체크
    public boolean isTokenValid(String token) {
        return jwtService.isTokenValid(token, true);
    }


    public RefreshTokensResponseDto refreshTokens(RefreshTokensRequestDto requestDto, String email) {

        String refreshToken = requestDto.getRefreshToken();
        if(refreshToken == null || isTokenValid(refreshToken)){
            throw new UnauthorizedException("Refresh Token is invalid or expired");
        }

        String newAccessToken = jwtService.generateAccessToken(email);
        String newRefreshToken = createRefreshToken(email);

        return RefreshTokensResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

    }
}
