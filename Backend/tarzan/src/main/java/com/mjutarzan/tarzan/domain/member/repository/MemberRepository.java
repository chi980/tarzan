package com.mjutarzan.tarzan.domain.member.repository;

import com.mjutarzan.tarzan.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long id);
    Optional<Member> findByProviderId(String providerId);

    Optional<Member> findByNickname(String nickname);

}
