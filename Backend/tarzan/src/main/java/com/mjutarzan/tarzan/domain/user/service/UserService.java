package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.api.dto.request.RegisterUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UpdateUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.response.RegisterUserResponseDto;
import com.mjutarzan.tarzan.domain.user.api.dto.response.UserResponseDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

public interface UserService {
    boolean isNicknameExists(String nickname);

    RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto, CustomUserDetails userDto);

    void updateUser(UpdateUserRequestDto updateUserRequestDto, CustomUserDetails userDto);

    UserResponseDto getUser(CustomUserDetails userDto);
}
