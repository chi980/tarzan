package com.mjutarzan.tarzan.global.security.token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository  extends CrudRepository<RefreshToken, String> {
    // 특정 refreshToken이 존재하는지 확인하는 메서드
    Boolean existsByRefreshToken(String refreshToken);

    // 특정 refreshToken을 삭제하는 메서드
    @Transactional
    void deleteByRefreshToken(String refreshToken);

}
