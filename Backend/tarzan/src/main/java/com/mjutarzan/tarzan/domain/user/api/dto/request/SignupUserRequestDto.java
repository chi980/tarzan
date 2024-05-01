package com.mjutarzan.tarzan.domain.user.api.dto.request;


import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupUserRequestDto {
    private String email;
    private String provider;
    private String providerId;
    private String nickname;
    private SiGunGu gu;
    private boolean haveAnimal;
    private boolean haveCar;
}
