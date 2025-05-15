package com.mjutarzan.tarzan.domain.fraud.service;


import com.mjutarzan.tarzan.domain.fraud.api.request.CreateUserQuizHistoryRequestDto;
import com.mjutarzan.tarzan.domain.fraud.api.response.TodayQuizResponseDto;
import com.mjutarzan.tarzan.domain.fraud.entity.Quiz;
import com.mjutarzan.tarzan.domain.fraud.entity.QuizHistory;
import com.mjutarzan.tarzan.domain.fraud.repository.QuizHistoryRepository;
import com.mjutarzan.tarzan.domain.fraud.repository.QuizRepository;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.DuplicateResourceException;
import com.mjutarzan.tarzan.global.common.exception.TimeExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;
    private final QuizHistoryRepository quizHistoryRepository;
    private final UserRepository userRepository;

    private static final LocalDate START_DATE = LocalDate.of(2025, 5, 15);

    @Override
    public TodayQuizResponseDto getTodayQuiz(CustomUserDetails userDto) {

        Quiz quiz = findTodayQuizEntity();

        // 2) 오늘 풀었는지, 맞혔는지 기록 조회
        QuizHistory history = quizHistoryRepository
                .findOneByUserIdAndSolvedDate(userDto.getId(), LocalDate.now());

        boolean alreadySolved  = history != null;
        Boolean solvedCorrect  = alreadySolved ? history.getIsCorrect() : null;

        return TodayQuizResponseDto
                .builder()
                    .id(quiz.getId())
                    .question(quiz.getQuestion())
                    .answer(quiz.getAnswer())
                    .explanation(quiz.getExplanation())
                    .alreadySolved(alreadySolved)
                    .solvedCorrect(solvedCorrect)
                .build();
    }

    @Override
    @Transactional
    public void solveQuiz(CreateUserQuizHistoryRequestDto requestDto, CustomUserDetails userDto) {
        log.info("{}",requestDto);
        Long userId = userDto.getId();
        LocalDate today = LocalDate.now();

        // 1. 이미 풀었는지 체크
        if (quizHistoryRepository.existsByUserIdAndSolvedDate(userId, today)) {
            throw new DuplicateResourceException("오늘 이미 퀴즈를 푸셨습니다.");
        }

        // 2. 오늘의 퀴즈 가져오기
        Quiz todayQuiz = findTodayQuizEntity();

        log.info("서버에서 퀴즈: {}", todayQuiz.getId());
        log.info("프론트에서 퀴즈: {}", requestDto.getQuizId());
        // 3. 요청된 ID 검증
        if (!todayQuiz.getId().equals(requestDto.getQuizId())) {
            throw new TimeExpiredException("풀이 시간이 지났습니다.");
        }

        // 4) 정답 여부 계산 및 저장
        Boolean isCorrect = todayQuiz.getAnswer() == requestDto.getUserAnswer();
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        log.info("isCorrect: {}", isCorrect);
        quizHistoryRepository.save(QuizHistory.builder()
                .quiz(todayQuiz)
                .user(user)
                .isCorrect(isCorrect)
                .solvedDate(LocalDate.now())
                .build()
        );
    }

    /**
     * 오늘 날짜 기준으로 DB에서 Quiz 한 건만 조회해서 반환합니다.
     */
    private Quiz findTodayQuizEntity() {
        int total = (int) quizRepository.count();
        long daysSince = ChronoUnit.DAYS.between(START_DATE, LocalDate.now());
        int offset = (int) (daysSince % total);
        var page = quizRepository.findAll(
                PageRequest.of(offset, 1, Sort.by("id").ascending())
        );
        return page.getContent()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "오늘의 퀴즈를 불러올 수 없습니다."
                ));
    }
}
