package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

import java.util.Optional;

public interface CustomUserDetailsService {
    Optional<CustomUserDetails> loadUserByEmail(String email);
}
