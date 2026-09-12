package com.shah.mini_quiz_system.dto.request;


import jakarta.validation.constraints.NotBlank;
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
public class QuestionRequest {

    @Positive
    private Long quizId;

    @NotBlank
    private String text;

    @NotNull
    @Positive
    private Integer score;

}
