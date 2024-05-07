package com.mjutarzan.tarzan.domain.member.model.dto;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Role role;
    private String name;
    private String username;
}