package com.shah.mini_quiz_system.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    @Positive
    private Integer score;


    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    @OneToMany(mappedBy = "submission")
    private List<Answer>answers;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
