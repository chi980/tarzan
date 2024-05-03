package com.mjutarzan.tarzan.domain.member.api;

import com.mjutarzan.tarzan.domain.member.api.dto.request.SignupRequestDto;
import com.mjutarzan.tarzan.domain.member.api.dto.response.SignupResponseDto;
import com.mjutarzan.tarzan.domain.member.service.MemberService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import com.mjutarzan.tarzan.global.exception.DuplicateUserException;
import com.mjutarzan.tarzan.global.exception.ProviderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/v1")
public class OauthApiV1 {

    private final MemberService userService;





    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto request){
        log.info("===========signup============");
        log.info("request:{}", request);

        try {
            SignupResponseDto signupResponse = userService.signup(request);
            BaseResponseDto response = BaseResponseDto.builder()
                    .success(true)
                    .data(signupResponse)
                    .build();
            log.info("signup 과정 success");
            return ResponseEntity.ok().body(response);
        }catch(DuplicateUserException e){
            BaseResponseDto response = BaseResponseDto.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            log.error("signup 과정 error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }catch(ProviderNotFoundException e){
            BaseResponseDto response  = BaseResponseDto.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            log.error("signup 과정 error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname){
        log.info("=============check_nickname===============");
        log.info(nickname);
        boolean isDuplicate = userService.checkNicknameDuplicate(nickname.strip());
        log.info("{}", isDuplicate);
        if(isDuplicate){
            log.error("닉네임이 중복되었습니다!");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else{
            return ResponseEntity.ok().build();
        }
    }


    /**
     * 로그인 메서드
     * 사용자가 로그인 하면 AccessToken과 Refresh Token을 생성하고 Redis에 저장
     * Refresh Token은 Redis에 저장되고, AccessToken은 클라이언트에 반환
//     * @param loginUserRequestDto
     * @return
     */
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@RequestBody LoginUserRequestDto loginUserRequestDto){
//
//        String providerId = userService.authenticate(loginUserRequestDto);
//
//        // 로그인 실패
//        if(providerId == null){
//            BaseResponseDto response = BaseResponseDto.builder().build();
//            response.setSuccess(false);
//            response.setMessage("없는 사용자입니다.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//
//        // 로그인 성공
//        log.info("jwt Secret: {}", jwtSecret);
//        log.info("access token valid time: {}", accessTokenValidTime);
//        // providerId를 Claim에 추가한 후 access Token 을 발급한다.
//        String accessToken = JwtTokenUtil.createToken(providerId, jwtSecret, accessTokenValidTime);
//        String refreshToken = null;
//
//        TokenDto token = TokenDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//
//        AuthenticateResponseDto response = AuthenticateResponseDto.builder()
//                .success(true)
//                .token(token)
//                .build();
//
//        return ResponseEntity.ok().body(response);
// }

//    @GetMapping("/info")
//    public String userInfoTest(Authentication auth){
//        User loginUser = userService.getLoginUserByLoginId(auth.getName());
//
//        return loginUser.toString();
//    }
}
