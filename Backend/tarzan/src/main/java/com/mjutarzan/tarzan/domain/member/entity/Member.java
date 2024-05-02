package com.mjutarzan.tarzan.domain.member.entity;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CommonEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String email; // 사용자 이메일

    @Column(name = "member_provider", nullable = false)
    private String provider; // OAuth2 제공자

    @Column(name = "member_provider_id", nullable = false, unique = true)
    private String providerId; // OAuth2 사용자의 고유한 ID, username 대신 사용

    @Column(name = "member_username")
    private String username;
    @Column(name = "member_name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role role;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_nickname", nullable = false, unique = true)
    private String nickname;


    @Column(name = "member_refresh_token")
    private String refreshToken;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "member_gu")
    private SiGunGu gu;

    @Column(name = "member_have_animal")
    @ColumnDefault("false")
    private boolean haveAnimal;

    @Column(name = "member_have_car")
    @ColumnDefault("false")
    private boolean haveCar;

    @Builder
    public Member(String email, String provider, String providerId, String username, String name, String password, String nickname, Role role, SiGunGu gu, boolean haveAnimal, boolean haveCar){
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.username = username;
        this.name = name;
        this.role = role;
        this.password = password;
        this.nickname = nickname;
        this.roles.add(role);
        this.gu = gu;
        this.haveAnimal = haveAnimal;
        this.haveCar = haveCar;
    }

    public void update(String email, String name){
        this.email = email;
        this.name = name;
    }
    public Member update(String nickname, SiGunGu gu, boolean haveAnimal, boolean haveCar){
        this.nickname = nickname;
        this.gu = gu;
        this.haveAnimal = haveAnimal;
        this.haveCar = haveCar;

        return this;
    }

    public void changeRefreshToken(String refreshToken) { // 추가!
        this.refreshToken = refreshToken;
    }

//    public String getRoleKey(){
//        return this.provider;
//    }
//
//    public String getLoginId(){
//        return this.providerId;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getKey())).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.providerId;
    }

    // 아래 항목은 UserDetail에서 사용하는 값이지만, 프로젝트에서 사용하지 않아 기본값인 true로 return
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
}