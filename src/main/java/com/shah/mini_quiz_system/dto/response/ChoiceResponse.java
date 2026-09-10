package com.shah.mini_quiz_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceResponse {

    private Long id;
    private String text;
    private boolean correct;
    private Long questionId;
}