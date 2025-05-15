package com.mjutarzan.tarzan.domain.fraud.entity;

import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Quiz extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Column(name = "quiz_question", columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(name = "quiz_answer", nullable = false)
    private Boolean answer;

    @Column(name = "quiz_explanation", columnDefinition = "TEXT", nullable = false)
    private String explanation;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuizHistory> quizHistoryList = new ArrayList<>();

    @Builder
    public Quiz(String question, Boolean answer, String explanation) {
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
    }

    public void addQuizHistoryList(QuizHistory quizHistory){
        this.quizHistoryList.add(quizHistory);
    }
}
