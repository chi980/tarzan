package com.mjutarzan.tarzan.domain.fraud.service;


import com.mjutarzan.tarzan.domain.fraud.api.response.TodayQuizResponseDto;
import com.mjutarzan.tarzan.domain.fraud.repository.QuizHistoryRepository;
import com.mjutarzan.tarzan.domain.fraud.repository.QuizRepository;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    private static final LocalDate START_DATE = LocalDate.of(2025, 5, 15);

    @Override
    public TodayQuizResponseDto getTodayQuiz(CustomUserDetails userDto) {

        int total = (int) quizRepository.count();
        long daysSince = ChronoUnit.DAYS.between(START_DATE, LocalDate.now());
        int offset = (int) (daysSince % total);
        var page = quizRepository.findAll(
                PageRequest.of(offset, 1, Sort.by("id").ascending())
        );

        Boolean alreadySolved = quizHistoryRepository
                .existsByUserIdAndSolvedDate(userDto.getId(), LocalDate.now());


        var quiz = page.getContent()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("퀴즈가 존재하지 않습니다."));

        return TodayQuizResponseDto
                .builder()
                    .id(quiz.getId())
                    .question(quiz.getQuestion())
                    .answer(quiz.getAnswer())
                    .explanation(quiz.getExplanation())
                .build();
    }
}
