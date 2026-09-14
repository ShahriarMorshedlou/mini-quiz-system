package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.domain.QuizStatus;
import com.shah.mini_quiz_system.domain.Submission;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import com.shah.mini_quiz_system.exception.BusinessException;
import com.shah.mini_quiz_system.exception.QuizNotFoundException;
import com.shah.mini_quiz_system.exception.SubmissionNotFoundException;
import com.shah.mini_quiz_system.mapper.SubmissionMapper;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import com.shah.mini_quiz_system.repoditory.SubmissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private SubmissionMapper submissionMapper;

    @InjectMocks
    private SubmissionService submissionService;

    @Test
    void startQuiz_shouldStartQuiz_whenQuizIsValid() {

        // Arrange
        Quiz quiz = new Quiz();
        quiz.setStatus(QuizStatus.PUBLISHED);

        Submission savedSubmission = new Submission();
        SubmissionResponse expectedResponse = new SubmissionResponse();

        when(quizRepository.findById(1L))
                .thenReturn(Optional.of(quiz));

        when(submissionRepository.save(any(Submission.class)))
                .thenReturn(savedSubmission);

        when(submissionMapper.toResponse(savedSubmission))
                .thenReturn(expectedResponse);

        // Act
        SubmissionResponse response =
                submissionService.startQuiz(1L);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void startQuiz_ifNotFoundQuiz_returnException() {
        // Arrange

        when(quizRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert

        assertThrows(
                QuizNotFoundException.class,
        () -> submissionService.startQuiz(1L)
        );

    }

    @Test
    void startQuiz_ifQuizIsNotPublished_throwException() {

        // Arrange
        Quiz quiz = new Quiz();
        quiz.setStatus(QuizStatus.DRAFT);

        when(quizRepository.findById(1L))
                .thenReturn(Optional.of(quiz));

        // Act + Assert
        assertThrows(
                BusinessException.class,
                () -> submissionService.startQuiz(1L)
        );
    }

    @Test
    void finishSubmission_shouldFinishSubmission_whenValid() {

        // Arrange
        Quiz quiz = new Quiz();
        quiz.setDuration(10);
        quiz.setQuestions(List.of());

        Submission submission = new Submission();
        submission.setQuiz(quiz);
        submission.setStartTime(LocalDateTime.now());
        submission.setAnswers(List.of());

        when(submissionRepository.findById(1L))
                .thenReturn(Optional.of(submission));

        SubmissionResponse expectedResponse = new SubmissionResponse();

        when(submissionMapper.toResponse(submission))
                .thenReturn(expectedResponse);

        // Act
        SubmissionResponse response =
                submissionService.finishSubmission(1L);

        // Assert
        assertEquals(expectedResponse, response);
        assertNotNull(submission.getEndTime());
    }

    @Test
    void finishSubmission_ifSubmissionNotFound_throwException() {

        // Arrange
        when(submissionRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                SubmissionNotFoundException.class,
                () -> submissionService.finishSubmission(1L)
        );
    }
}