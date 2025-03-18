package com.mjutarzan.tarzan.global.jwt.service;

import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import com.mjutarzan.tarzan.global.jwt.JwtTokenProvider;
import com.mjutarzan.tarzan.global.jwt.api.request.LogoutRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.request.ReIssueTokensRequestDto;
import com.mjutarzan.tarzan.global.jwt.api.response.ReIssueTokensResponseDto;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public ReIssueTokensResponseDto reissueAccessToken(ReIssueTokensRequestDto requestDto, String refreshToken) {
        String email = requestDto.getEmail();

        // Refresh Token 검증
        if (!jwtTokenProvider.validateRefreshToken(email, refreshToken)) {
            throw new UnauthorizedException("Invalid Refresh Token");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(email);


        return ReIssueTokensResponseDto.builder()
                .accessToken(newAccessToken)
                .build();

    }

    public void logout(LogoutRequestDto requestDto) {
        String email = requestDto.getEmail();
        jwtTokenProvider.removeRefreshToken(email);
    }

    public Cookie reissueRefreshToken(ReIssueTokensRequestDto requestDto) {
        String email = requestDto.getEmail();
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(email);
        jwtTokenProvider.removeRefreshToken(email);
        jwtTokenProvider.saveRefreshToken(email, newRefreshToken);

        Cookie refreshTokenCookie = jwtTokenProvider.generateRefreshTokenCookie(newRefreshToken);

        return refreshTokenCookie;
    }
}
