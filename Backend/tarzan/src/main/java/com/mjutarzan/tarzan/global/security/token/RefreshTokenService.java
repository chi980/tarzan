package com.mjutarzan.tarzan.global.security.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken save(String accessToken, String refreshToken, String memberUserName, Long expiration){
        return refreshTokenRepository.save(new RefreshToken(accessToken, refreshToken, memberUserName, expiration));
    }
    public void removeRefreshToken(String accessToken) {
        RefreshToken token = refreshTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(IllegalArgumentException::new);

        refreshTokenRepository.delete(token);
    }

    public Optional<RefreshToken> findByAccessToken(String accessToken) {
        return refreshTokenRepository.findByAccessToken(accessToken);
    }

    public RefreshToken save(RefreshToken resultToken) {
        return refreshTokenRepository.save(resultToken);
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshTokenString) {
        return refreshTokenRepository.findById(refreshTokenString);
    }
}
