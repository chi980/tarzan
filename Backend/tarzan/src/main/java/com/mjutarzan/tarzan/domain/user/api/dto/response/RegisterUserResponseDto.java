package com.mjutarzan.tarzan.domain.user.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder
public class RegisterUserResponseDto {

    @JsonProperty("user_email")
    private String email;

    @JsonProperty("user_role")
    private Role role;

}
