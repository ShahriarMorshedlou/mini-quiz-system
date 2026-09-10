package com.shah.mini_quiz_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {

    private Long id;
    private Long choiceId;
    private Long questionId;
    private Long submissionId;
}