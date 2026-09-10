package com.shah.mini_quiz_system.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;


    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    public Answer(Choice choice, Question question, Submission submission) {
        this.choice = choice;
        this.question = question;
        this.submission = submission;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
