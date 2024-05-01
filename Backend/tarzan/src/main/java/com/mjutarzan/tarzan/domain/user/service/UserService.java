package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.api.dto.request.LoginUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.SignupUserRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;

public interface UserService {
    User getLoginUserByLoginId(String loginId);

    boolean checkProviderIdDuplicate(String providerId);

    boolean checkNicknameDuplicate(String nickname);

    void signup(SignupUserRequestDto signupUserRequestDto) throws ProviderNotFoundException;

    String authenticate(LoginUserRequestDto loginUserRequestDto);
}
