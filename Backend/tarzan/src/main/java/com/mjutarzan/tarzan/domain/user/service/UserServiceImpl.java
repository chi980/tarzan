package com.mjutarzan.tarzan.domain.user.service;

import com.mjutarzan.tarzan.domain.user.api.dto.request.RegisterUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.UpdateUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.response.RegisterUserResponseDto;
import com.mjutarzan.tarzan.domain.user.api.dto.response.UserResponseDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.service.LocationService;
import com.mjutarzan.tarzan.global.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final LocationService locationService;
    private final JwtService jwtService;

    @Override
    public boolean isNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto, UserDto loginedUserDto) {
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        Point jobLocation = null;
        if(requestDto.getLatitude() != null && requestDto.getLongitude() != null){
            jobLocation = locationService.createPoint(requestDto.getLatitude(), requestDto.getLongitude());
        }

        loginedUser.updateUser(requestDto, jobLocation);
        String refreshToken = jwtService.createRefreshToken();
        loginedUser.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(loginedUser);
        return RegisterUserResponseDto.builder()
                .refreshToken(refreshToken)
                .userRole(loginedUser.getRole())
                .build();
    }

    @Override
    public void updateUser(UpdateUserRequestDto requestDto, UserDto loginedUserDto) {
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        Point jobLocation = null;
        if(requestDto.getLatitude() != null && requestDto.getLongitude() != null){
            jobLocation = locationService.createPoint(requestDto.getLatitude(), requestDto.getLongitude());
        }

        loginedUser.updateUser(requestDto, jobLocation);
    }

    @Override
    public UserResponseDto getUser(UserDto loginedUserDto) {
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        return loginedUser.getInstance();
    }

}
