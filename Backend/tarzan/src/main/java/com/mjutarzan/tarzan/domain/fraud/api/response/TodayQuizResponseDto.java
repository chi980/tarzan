package com.mjutarzan.tarzan.domain.fraud.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodayQuizResponseDto {

    @JsonProperty("quiz_id")
    private Long id;

    @JsonProperty("quiz_question")
    private String question;

    @JsonProperty("quiz_answer")
    private Boolean answer;

    @JsonProperty("quiz_explanation")
    private String explanation;

    @JsonProperty("quiz_already_solved")
    private Boolean alreadySolved;

    @JsonProperty("quiz_solved_correct")
    private Boolean solvedCorrect;


    @Builder
    public TodayQuizResponseDto(Long id, String question, Boolean answer, String explanation, Boolean alreadySolved, Boolean solvedCorrect) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.alreadySolved = alreadySolved;
        this.solvedCorrect = solvedCorrect;
    }
}
