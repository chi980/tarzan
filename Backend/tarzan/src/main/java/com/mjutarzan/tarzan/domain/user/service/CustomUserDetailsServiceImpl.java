package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.jwt.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{

    private final UserRepository userRepository;

    /**
     * email을 받아서 사용자를 찾고 CustomUserDetails 객체를 반환
     * 만약, password가 없는 sns 사용자라면 PasswordUtil로 새 비밀번호를 생성하여 설정
     * @param email
     * @return
     */
    @Override
    public Optional<CustomUserDetails> loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> CustomUserDetails.getInstance(
                        user,
                        Optional.ofNullable(user.getPassword()).orElseGet(PasswordUtil::generateRandomPassword)
                ));
    }
}
