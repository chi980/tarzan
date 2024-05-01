package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.api.dto.request.LoginUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.SignupUserRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getLoginUserByLoginId(String loginId) {

        Optional<User> loginUser = userRepository.findByProviderId(loginId);

        if(!loginUser.isEmpty()) return loginUser.get();
        return null;
    }

    @Override
    public boolean checkProviderIdDuplicate(String providerId) {
        Optional<User> loginUser = userRepository.findByProviderId(providerId);

        if(!loginUser.isEmpty()) return true;
        return false;
    }

    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        Optional<User> loginUser = userRepository.findByNickname(nickname);

        if(!loginUser.isEmpty()) return true;
        return false;
    }

    @Override
    public void signup(SignupUserRequestDto signupUserRequestDto) throws ProviderNotFoundException{
        Role role = null;
        if(signupUserRequestDto.getProvider().equals("google")) role = Role.GOOGLE;
        else if(signupUserRequestDto.getProvider().equals("kakao")) role = Role.KAKAO;
        else {
            throw new ProviderNotFoundException("지원하지 않는 provider입니다.");
        }

        User user = User.builder()
                .email(signupUserRequestDto.getEmail())
                .provider(signupUserRequestDto.getProvider())
                .providerId(signupUserRequestDto.getProviderId())
                .nickname(signupUserRequestDto.getNickname())
                .role(role)
                .gu(signupUserRequestDto.getGu())
                .haveAnimal(signupUserRequestDto.isHaveAnimal())
                .haveCar(signupUserRequestDto.isHaveCar())
                .build();
        userRepository.save(user);
    }

    @Override
    public String authenticate(LoginUserRequestDto loginUserRequestDto) {
        // 로그인 로직(redis)
        return null;
    }
}
