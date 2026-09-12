package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Choice;
import com.shah.mini_quiz_system.domain.Question;
import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.domain.QuizStatus;
import com.shah.mini_quiz_system.dto.request.QuizRequest;
import com.shah.mini_quiz_system.dto.response.QuizResponse;
import com.shah.mini_quiz_system.exception.BusinessException;
import com.shah.mini_quiz_system.exception.QuizNotFoundException;
import com.shah.mini_quiz_system.mapper.QuizMapper;
import com.shah.mini_quiz_system.repoditory.ChoiceRepository;
import com.shah.mini_quiz_system.repoditory.QuestionRepository;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final ChoiceRepository choiceRepository;

    public QuizService(QuizRepository quizRepository
            , QuizMapper quizMapper
            , ChoiceRepository choiceRepository
    ) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.choiceRepository = choiceRepository;
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

    public List<QuizResponse> getAllQuizzes() {

        List<Quiz> quizList = quizRepository.findAll();

        return quizMapper.toResponseList(quizList);

    }

    public QuizResponse getQuizById(Long id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found With Id: " + id));

        return quizMapper.toResponse(quiz);
    }


    @Transactional
    public QuizResponse updateQuiz(QuizRequest request, Long id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found With Id: " + id));


        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setDuration(request.getDuration());
        quiz.setStatus(request.getStatus());

        return quizMapper.toResponse(quiz);
    }

    @Transactional
    public void deleteQuizById(Long id) {


        if (!quizRepository.existsById(id)) {
            throw new QuizNotFoundException("Quiz Not Found With Id: " + id);
        }
        quizRepository.deleteById(id);
    }

    @Transactional
    public QuizResponse publishQuiz(Long id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found With Id: " + id));


        // Quiz must be in DRAFT state
        if (quiz.getStatus() != QuizStatus.DRAFT) {
            throw new BusinessException("Only DRAFT quizzes can be published"
            );
        }

        // Quiz must have at least one question
        if (quiz.getQuestions().isEmpty()) {
            throw new BusinessException(
                    "Quiz must have at least one question"
            );
        }


        // Every question must have at least two choices
        for (Question question : quiz.getQuestions()) {

            if (question.getChoices().size() < 2) {
                throw new BusinessException(
                        "Each question must have at least two choices"
                );
            }

            // Every question must have exactly one correct choice
            long correctChoiceCount = question.getChoices()
                    .stream()
                    .filter(choice -> choice.isCorrect())
                    .count();

            if (correctChoiceCount != 1) {
                throw new BusinessException(
                        "Each question must have exactly one correct choice"
                );
            }

        }

        quiz.setStatus(QuizStatus.PUBLISHED);

        return quizMapper.toResponse(quiz);

    }

}
