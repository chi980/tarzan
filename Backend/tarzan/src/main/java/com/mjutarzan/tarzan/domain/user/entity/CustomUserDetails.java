package com.mjutarzan.tarzan.domain.user.entity;


import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
@ToString(of = {"email", "role", "nickname","gu"})
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email; // 사용자 이름
    private String password; // 비밀번호
    private Role role;
    private String imageUrl;
    private String nickname;
    private SiGunGu gu;
    private boolean haveAnimal;
    private boolean haveCar;
    private String jobAddress;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return this.getEmail();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetails getInstance(User user, String password){
        return CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(password)
                .role(user.getRole())
                .imageUrl(user.getImageUrl())
                .nickname(user.getNickname())
                .gu(user.getGu())
                .haveAnimal(user.isHaveAnimal())
                .haveCar(user.isHaveCar())
                .jobAddress(user.getJobAddress())
                .build();
    }

}
