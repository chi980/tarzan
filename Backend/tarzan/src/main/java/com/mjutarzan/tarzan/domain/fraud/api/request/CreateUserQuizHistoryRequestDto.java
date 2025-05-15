package com.mjutarzan.tarzan.domain.fraud.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateUserQuizHistoryRequestDto {

    @JsonProperty("quiz_id")
    @NotNull
    private Long quizId;

    @JsonProperty("user_answer")
    @NotNull
    private Boolean userAnswer;
}

