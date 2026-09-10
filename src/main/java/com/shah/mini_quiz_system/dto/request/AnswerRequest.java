package com.shah.mini_quiz_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NotNull
    @Positive
    private Long choiceId;

    @NotNull
    @Positive
    private Long questionId;

    @NotNull
    @Positive
    private Long submissionId;
}