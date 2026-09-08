package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.dto.request.QuizRequest;
import com.shah.mini_quiz_system.dto.response.QuizResponse;
import com.shah.mini_quiz_system.mapper.QuizMapper;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    public QuizService(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    @Transactional
    public QuizResponse createQuiz(QuizRequest request) {

        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .status(request.getStatus())
                .build();
        Quiz savedQuiz = quizRepository.save(quiz);

        return quizMapper.toResponse(savedQuiz);

    }
}
