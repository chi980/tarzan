package com.mjutarzan.tarzan.domain.fraud.service;

import com.mjutarzan.tarzan.domain.fraud.api.request.CreateUserQuizHistoryRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.TodayQuizResponseDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

public interface QuizService {
    TodayQuizResponseDto getTodayQuiz(CustomUserDetails userDto);

    void solveQuiz(CreateUserQuizHistoryRequestDto requestDto, CustomUserDetails userDto);
}
