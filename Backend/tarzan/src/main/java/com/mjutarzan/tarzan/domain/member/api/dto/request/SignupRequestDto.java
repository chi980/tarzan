package com.mjutarzan.tarzan.domain.member.api.dto.request;


import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequestDto {
    private String email;
    private String provider;
    private String providerId;
    private String nickname;
    private SiGunGu gu;
    private boolean haveAnimal;
    private boolean haveCar;
}
