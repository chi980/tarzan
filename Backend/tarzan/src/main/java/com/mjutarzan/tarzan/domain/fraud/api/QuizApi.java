package com.mjutarzan.tarzan.domain.fraud.api;

import com.mjutarzan.tarzan.domain.fraud.api.response.TodayQuizResponseDto;
import com.mjutarzan.tarzan.domain.fraud.service.QuizService;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizApi {

    private final QuizService quizService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodayQuiz(@AuthenticationPrincipal CustomUserDetails userDto){
        TodayQuizResponseDto result = quizService.getTodayQuiz(userDto);
        return ResponseEntity.ok().body(
                BaseResponseDto.builder()
                        .success(true)
                        .message("완료되었습니다.")
                        .data(result)
                        .build()
        );
    }


}
