package com.shah.mini_quiz_system.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String description; // duration in minutes

    private Integer duration;

    @Enumerated(EnumType.STRING)
    private QuizStatus status;


    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz")
    private List<Submission> submissions;


    public Quiz(String title, String description, Integer duration, QuizStatus status) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.status = status;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setStatus(QuizStatus status) {
        this.status = status;
    }
}
