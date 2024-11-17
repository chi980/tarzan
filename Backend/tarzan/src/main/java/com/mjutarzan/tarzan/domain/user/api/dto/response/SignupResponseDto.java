package com.mjutarzan.tarzan.domain.user.api.dto.response;

import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private String providerId;
    private SiGunGu gu;
    private boolean haveAnimal;
    private boolean haveCar;
}
