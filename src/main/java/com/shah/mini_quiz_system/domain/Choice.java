package com.shah.mini_quiz_system.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String text;

    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "selectedChoice")
    private List<Answer> answers;

    public Choice(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public void setText(String text) {
        this.text = text;
    }
}
