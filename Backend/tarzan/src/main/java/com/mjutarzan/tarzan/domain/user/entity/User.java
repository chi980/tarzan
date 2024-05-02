package com.mjutarzan.tarzan.domain.user.entity;

import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String email; // 사용자 이메일

    @Column(name = "user_provider", nullable = false)
    private String provider; // OAuth2 제공자

    @Column(name = "user_provider_id", nullable = false, unique = true)
    private String providerId; // OAuth2 사용자의 고유한 ID

    @Column(name = "user_nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "user_refresh_token")
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role role;

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
    public User(String email, String provider, String providerId, String nickname, Role role, SiGunGu gu, boolean haveAnimal, boolean haveCar){
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.nickname = nickname;
        this.role = role;
        this.gu = gu;
        this.haveAnimal = haveAnimal;
        this.haveCar = haveCar;
    }

    public User update(String nickname, SiGunGu gu, boolean haveAnimal, boolean haveCar){
        this.nickname = nickname;
        this.gu = gu;
        this.haveAnimal = haveAnimal;
        this.haveCar = haveCar;

        return this;
    }

    public void changeRefreshToken(String refreshToken) { // 추가!
        this.refreshToken = refreshToken;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public String getLoginId(){
        return this.providerId;
    }
}