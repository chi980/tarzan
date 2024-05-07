package com.mjutarzan.tarzan.domain.member.api;

import com.mjutarzan.tarzan.global.security.dto.StatusResponseDto;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import com.mjutarzan.tarzan.global.security.token.GeneratedToken;
import com.mjutarzan.tarzan.global.security.token.RefreshToken;
import com.mjutarzan.tarzan.global.security.token.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/token")
public class TokenApi {
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") final String accessToken) {
        // 엑세스 토큰으로 현재 Redis 정보 삭제
        refreshTokenService.removeRefreshToken(accessToken);
        return ResponseEntity.ok().body(StatusResponseDto.success());
    }

    @PostMapping("/refresh")
//    public ResponseEntity<?> refresh(@RequestHeader("Authorization") final String accessToken) {
    public ResponseEntity<?> refresh(@RequestBody
                                         String refreshTokenString) {
        log.info("client에서 받은 refresh token: {}",refreshTokenString);

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> refreshToken = refreshTokenService.findByRefreshToken(refreshTokenString);
        // RefreshToken이 존재하고 유효하다면 실행
        if (refreshToken.isPresent() && !jwtTokenUtil.isExpired(refreshToken.get().getRefreshToken())) {
            // RefreshToken 객체를 꺼내온다.
            RefreshToken resultToken = refreshToken.get();
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = jwtTokenUtil.createAccessToken(jwtTokenUtil.getUsername(resultToken.getRefreshToken()), jwtTokenUtil.getRole(resultToken.getRefreshToken()));
            // 액세스 토큰의 값을 수정해준다.
            resultToken.updateAccessToken(newAccessToken);
            refreshTokenService.save(resultToken);

            GeneratedToken token = GeneratedToken.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken.get().getRefreshToken())
                    .build();
            log.error("찾아낸 refreshToken: {}", refreshToken.get());
            log.error("refresh token is expired: {}", !jwtTokenUtil.isExpired(refreshToken.get().getRefreshToken()));
            // 새로운 액세스 토큰을 반환해준다.
            return ResponseEntity.ok().body(StatusResponseDto.success(token));
        }else{
            return ResponseEntity.badRequest().body(StatusResponseDto.fail("issue token fail"));
        }
    }
}
