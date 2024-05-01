package com.mjutarzan.tarzan.domain.user.repository;

import com.mjutarzan.tarzan.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByProviderId(String providerId);

    Optional<User> findByNickname(String nickname);

}
