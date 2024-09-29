package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.api.dto.request.RegisterUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UpdateUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.response.UserResponseDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface UserService {
    boolean isNicknameExists(String nickname);

    void registerUser(RegisterUserRequestDto registerUserRequestDto, UserDto userDto);

    void updateUser(UpdateUserRequestDto updateUserRequestDto, UserDto userDto);

    UserResponseDto getUser(UserDto userDto);
}
