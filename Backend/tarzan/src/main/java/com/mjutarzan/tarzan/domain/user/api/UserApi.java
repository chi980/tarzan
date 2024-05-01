package com.mjutarzan.tarzan.domain.user.api;

import com.mjutarzan.tarzan.domain.user.api.dto.request.LoginUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.SignupUserRequestDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.service.UserService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class UserApi {

    private final UserService userService;

    @Value("{spring.jwt.secret}")
    private String jwtSecret;

    @Value("${spring.jwt.access-token-validity-in-seconds}")
    private Long accessTokenValidTime;

    @Value("${spring.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenValidTime;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupUserRequestDto signupUserRequestDto){
        log.info("===========signup============");
        log.info("response:{}", signupUserRequestDto);

        BaseResponseDto response = BaseResponseDto.builder().build();

        log.info("{}",userService.checkProviderIdDuplicate(signupUserRequestDto.getProviderId()));
        log.info("{}",userService.checkProviderIdDuplicate(signupUserRequestDto.getNickname()));


        if(userService.checkProviderIdDuplicate(signupUserRequestDto.getProviderId())){
            response.setSuccess(false);
            response.setMessage("중복된 사용자입니다.");
            log.error("중복된 사용자입니다!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        if(userService.checkNicknameDuplicate(signupUserRequestDto.getNickname())){
            response.setSuccess(false);
            response.setMessage("닉네임이 중복됩니다.");
            log.error("닉네임이 중복됩니다!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        try{
            userService.signup(signupUserRequestDto);
            response.setSuccess(true);
            response.setMessage("회원가입 성공");
            log.info("성공했습니다!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (ProviderNotFoundException e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserRequestDto loginUserRequestDto){
        BaseResponseDto response = BaseResponseDto.builder().build();

        String providerId = userService.authenticate(loginUserRequestDto);

        // 로그인 실패
        if(providerId == null){
            response.setSuccess(false);
            response.setMessage("없는 사용자입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 로그인 성공
        String accessToken = JwtTokenUtil.createToken(providerId, jwtSecret, accessTokenValidTime);
        response.setSuccess(true);
        response.setData(Collections.singletonMap("accessToken", accessToken));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/info")
    public String userInfoTest(Authentication auth){
        User loginUser = userService.getLoginUserByLoginId(auth.getName());

        return loginUser.toString();
    }
}
