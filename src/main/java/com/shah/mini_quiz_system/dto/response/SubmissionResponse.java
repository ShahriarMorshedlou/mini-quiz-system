package com.shah.mini_quiz_system.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponse {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Long quizId;

}
