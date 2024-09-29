package com.mjutarzan.tarzan.domain.user.api;

import com.mjutarzan.tarzan.domain.user.api.dto.request.RegisterUserRequestDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.service.UserService;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserApi {

    private final UserService userService;

    @PostMapping("/user/check")
    public ResponseEntity<?> checkUnique(@RequestBody(required = true) String nickname, @AuthenticationPrincipal UserDto userDto){
        if (nickname == null || nickname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                            .success(false)
                            .message("닉네임은 필수 값입니다.")
                    .build());
        }
        // 2. 닉네임이 이미 존재하는 경우 Conflict 응답
        boolean nicknameExists = userService.isNicknameExists(nickname); // UserService를 통해 닉네임 존재 여부 확인
        if (nicknameExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponseDto.builder()
                    .success(false)
                    .message("이미 존재하는 닉네임입니다.")
                    .build());
        }

        // 3. 닉네임이 존재하지 않는 경우 OK 응답
        return ResponseEntity.ok(BaseResponseDto.builder()
                .success(true)
                .message("사용 가능한 닉네임입니다.")
                .build());
    }

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequestDto registerUserRequestDto, BindingResult bindingResult , @AuthenticationPrincipal UserDto userDto){

        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .success(false)
                    .message("유효성 검사 오류")
                    .build());
        }



        return ResponseEntity.ok().build();
    }
}
