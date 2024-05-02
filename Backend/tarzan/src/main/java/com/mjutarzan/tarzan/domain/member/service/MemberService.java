package com.mjutarzan.tarzan.domain.member.service;

import com.mjutarzan.tarzan.domain.member.api.dto.request.SignupRequestDto;
import com.mjutarzan.tarzan.domain.member.api.dto.response.SignupResponseDto;
import com.mjutarzan.tarzan.domain.member.entity.Member;
import com.mjutarzan.tarzan.global.exception.DuplicateUserException;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;

public interface MemberService {
    Member getLoginUserByLoginId(String loginId);

    boolean checkProviderIdDuplicate(String providerId);

    boolean checkNicknameDuplicate(String nickname);

    void signupV1(SignupRequestDto signupUserRequestDto) throws ProviderNotFoundException;


    SignupResponseDto signup(SignupRequestDto signupUserRequestDto)  throws DuplicateUserException, ProviderNotFoundException;
}
