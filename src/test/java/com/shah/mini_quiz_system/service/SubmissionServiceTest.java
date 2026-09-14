package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.domain.QuizStatus;
import com.shah.mini_quiz_system.domain.Submission;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import com.shah.mini_quiz_system.mapper.SubmissionMapper;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import com.shah.mini_quiz_system.repoditory.SubmissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}