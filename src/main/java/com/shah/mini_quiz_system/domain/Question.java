package com.shah.mini_quiz_system.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String text;

    @NotNull
    @Positive
    private Integer score;


    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    @OneToMany(mappedBy = "question")
    private List<Choice> choices;


    @OneToMany(mappedBy = "question")
    private List<Answer> answers;


    public Question(String text, Integer score) {

        this.text = text;
        this.score = score;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
