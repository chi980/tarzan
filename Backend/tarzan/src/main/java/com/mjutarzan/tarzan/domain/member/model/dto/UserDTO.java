package com.mjutarzan.tarzan.domain.member.model.dto;

import com.mjutarzan.tarzan.domain.member.model.vo.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Role role;
    private String name;
    private String username;
}