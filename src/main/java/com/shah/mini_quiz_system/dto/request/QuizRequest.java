package com.shah.mini_quiz_system.dto.request;

import com.shah.mini_quiz_system.domain.QuizStatus;
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
public class QuizRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private Integer duration;

    private QuizStatus status;

}
