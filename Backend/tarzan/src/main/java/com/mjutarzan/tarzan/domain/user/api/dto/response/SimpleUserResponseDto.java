package com.mjutarzan.tarzan.domain.user.api.dto.response;

import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimpleUserResponseDto {
    private String email;
    private String nickname;
    private Role role;
    private SiGunGu gu;
}
