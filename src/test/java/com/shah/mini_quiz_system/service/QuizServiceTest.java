package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Choice;
import com.shah.mini_quiz_system.domain.Question;
import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.domain.QuizStatus;
import com.shah.mini_quiz_system.dto.response.QuizResponse;
import com.shah.mini_quiz_system.exception.BusinessException;
import com.shah.mini_quiz_system.exception.QuizNotFoundException;
import com.shah.mini_quiz_system.mapper.QuizMapper;
import com.shah.mini_quiz_system.repoditory.ChoiceRepository;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizMapper quizMapper;

    @Mock
    private ChoiceRepository choiceRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    void publishQuiz_shouldPublishQuiz_whenQuizIsValid() {

        //Arrange

        Quiz quiz = new Quiz();
        quiz.setStatus(QuizStatus.DRAFT);

        Question question = new Question();


        Choice correctChoice = new Choice();
        correctChoice.setCorrect(true);

        Choice wrongChoice = new Choice();
        wrongChoice.setCorrect(false);

        question.setChoices(List.of(correctChoice, wrongChoice));
        quiz.setQuestions(List.of(question));

        when(quizRepository.findById(1L))
                .thenReturn(Optional.of(quiz));

        //Act

        QuizResponse response = quizService.publishQuiz(1L);

        //Assert

        assertEquals(QuizStatus.PUBLISHED, quiz.getStatus());

    }

    @Test
    void publishQuiz_ifNotFoundQuiz_returnException(){

        //Arrange

        when(quizRepository.findById(1L))
                .thenReturn(Optional.empty());


        //Act

        //Assert
        assertThrows(
                QuizNotFoundException.class,
                () -> quizService.publishQuiz(1L)
        );
    }

    @Test
    void publishQuiz_ifQuizIsNotDraft_throwException() {

        // Arrange
        Quiz quiz = new Quiz();
        quiz.setStatus(QuizStatus.PUBLISHED);

        when(quizRepository.findById(1L))
                .thenReturn(Optional.of(quiz));

        // Act + Assert
        assertThrows(
                BusinessException.class,
                () -> quizService.publishQuiz(1L)
        );
    }

    @Test
    void publishQuiz_ifQuizHasNoQuestions_throwException() {

        // Arrange
        Quiz quiz = new Quiz();
        quiz.setStatus(QuizStatus.DRAFT);
        quiz.setQuestions(List.of());

        when(quizRepository.findById(1L))
                .thenReturn(Optional.of(quiz));

        // Act + Assert
        assertThrows(
                BusinessException.class,
                () -> quizService.publishQuiz(1L)
        );
    }
}
