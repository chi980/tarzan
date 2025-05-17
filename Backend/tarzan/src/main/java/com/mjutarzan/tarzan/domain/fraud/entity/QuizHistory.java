package com.mjutarzan.tarzan.domain.fraud.entity;

import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quiz_history",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user","solved_date"}))
public class QuizHistory extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_history_id")
    private Long id;

    @Column(name="quiz_history_is_correct",nullable = false)
    private Boolean isCorrect;

    @Column(name="quiz_history_solved_date", nullable=false)
    private LocalDate solvedDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_history_quiz")
    private Quiz quiz;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_history_user")
    private User user;


    @Builder
    public QuizHistory(Boolean isCorrect, LocalDate solvedDate, Quiz quiz, User user) {
        this.isCorrect = isCorrect;
        this.solvedDate = solvedDate;
        this.quiz = quiz;
        this.user = user;

        quiz.addQuizHistoryList(this);
        user.addQuizHistoryList(this);
    }
}
