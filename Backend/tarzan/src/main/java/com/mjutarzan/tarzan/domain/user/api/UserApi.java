package com.mjutarzan.tarzan.domain.user.api;

import com.mjutarzan.tarzan.domain.user.api.dto.request.LoginUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.request.SignupUserRequestDto;
import com.mjutarzan.tarzan.domain.user.api.dto.response.AuthenticateResponseDto;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.service.UserService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;
import com.mjutarzan.tarzan.global.security.jwt.JwtTokenUtil;
import com.mjutarzan.tarzan.global.security.model.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class UserApi {

    private final UserService userService;

    @Value("${spring.jwt.secret}")
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

    /**
     * 로그인 메서드
     * 사용자가 로그인 하면 AccessToken과 Refresh Token을 생성하고 Redis에 저장
     * Refresh Token은 Redis에 저장되고, AccessToken은 클라이언트에 반환
     * @param loginUserRequestDto
     * @return
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserRequestDto loginUserRequestDto){

        String providerId = userService.authenticate(loginUserRequestDto);

        // 로그인 실패
        if(providerId == null){
            BaseResponseDto response = BaseResponseDto.builder().build();
            response.setSuccess(false);
            response.setMessage("없는 사용자입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 로그인 성공
        log.info("jwt Secret: {}", jwtSecret);
        log.info("access token valid time: {}", accessTokenValidTime);
        // providerId를 Claim에 추가한 후 access Token 을 발급한다.
        String accessToken = JwtTokenUtil.createToken(providerId, jwtSecret, accessTokenValidTime);
        String refreshToken = null;

        TokenDto token = TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        AuthenticateResponseDto response = AuthenticateResponseDto.builder()
                .success(true)
                .token(token)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/info")
    public String userInfoTest(Authentication auth){
        User loginUser = userService.getLoginUserByLoginId(auth.getName());

        return loginUser.toString();
    }
}
