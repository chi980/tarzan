package com.mjutarzan.tarzan.domain.user.model.dto;


import com.mjutarzan.tarzan.domain.user.entity.User;
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
public class UserDto implements UserDetails {
    private String email;
    private String password;
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
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDto getInstance(User user, String password){
        return UserDto.builder()
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
