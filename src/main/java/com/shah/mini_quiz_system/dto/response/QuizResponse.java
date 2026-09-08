package com.shah.mini_quiz_system.dto.response;

import com.shah.mini_quiz_system.domain.QuizStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private QuizStatus status;
}
