package com.mjutarzan.tarzan.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.refresh-secret-key")
    private String REFRESH_SECRET_KEY;

    @Value("${jwt.access.expiration}")
    private Long ACCESS_EXPIRATION ;

    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXPIRATION ;

    @Value("${jwt.access.header}")
    private String ACCESS_HEADER;

    @Value("${jwt.refresh.header}")
    private String REFRESH_HEADER;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    /**
     * AccessToken 생성 메소드
     */
    public String generateAccessToken(String email) {
        return genearteToken(email, ACCESS_EXPIRATION, SECRET_KEY);
    }

    /**
     * RefreshToken 생성 메소드
     */
    public String generateRefreshToken(String email) {
        String refreshToken = genearteToken(email, REFRESH_EXPIRATION, REFRESH_SECRET_KEY);
        this.saveRefreshToken(email, refreshToken);
        return refreshToken;
    }

    /**
     * 공통 토큰 생성 메소드
     */
    private String genearteToken(String email, long validity, String key) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);

        return  JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(Algorithm.HMAC512(key));
    }

    /**
     * Jwt에서 사용자명(이메일) 추출
     */
    public Optional<String> getEmail(String accessToken) {
        return Optional.ofNullable(JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(accessToken)
                .getClaim(EMAIL_CLAIM)
                .asString());

    }

    /**
     * Access Token 검증
     */

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     *  Refresh Token 검증
     */

    public boolean validateRefreshToken(String email, String token) {
        try {
            JWT.require(Algorithm.HMAC512(REFRESH_SECRET_KEY)).build().verify(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
//        return refreshTokenStore.containsKey(username) && refreshTokenStore.get(username).equals(refreshToken);
    }


    /**
     *  헤더에서 토큰 추출
     */
    public Optional<String> resolveToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ACCESS_HEADER))
                .filter(token -> token.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    // Refresh Token 삭제 (로그아웃 시)
    public void removeRefreshToken(String email) {
//        refreshTokenStore.remove(username);
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(null),
                        () -> {
                            // 이메일로 유저가 없으면 로깅 혹은 예외 처리
                            log.error("No user found with email: {}", email);
                        });
    }


    public Optional<Date> getExpirationDateFromToken(String token, Boolean isRefresh) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(!isRefresh?SECRET_KEY:REFRESH_SECRET_KEY))
                    .build()
                    .verify(token)
                    .getExpiresAt());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void saveRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.updateRefreshToken(refreshToken);
    }
}