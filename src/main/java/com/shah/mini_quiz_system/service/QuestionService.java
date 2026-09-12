package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Question;
import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.dto.request.QuestionRequest;
import com.shah.mini_quiz_system.dto.response.QuestionResponse;

import com.shah.mini_quiz_system.exception.QuestionNotFoundException;
import com.shah.mini_quiz_system.exception.QuizNotFoundException;
import com.shah.mini_quiz_system.mapper.QuestionMapper;
import com.shah.mini_quiz_system.repoditory.QuestionRepository;
import com.shah.mini_quiz_system.repoditory.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuizRepository quizRepository;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionMapper questionMapper,
                           QuizRepository quizRepository
    ) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.quizRepository =quizRepository;
    }

    @Transactional
    public QuestionResponse createQuestion(QuestionRequest request) {

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() ->
                        new QuizNotFoundException(
                                "Quiz Not Found With Id: " + request.getQuizId()
                        ));

        Question question = new Question(
                request.getText(),
                request.getScore()
        );

        question.setQuiz(quiz);

        Question savedQuestion = questionRepository.save(question);

        return questionMapper.toResponse(savedQuestion);
    }

    public List<QuestionResponse> getAllQuestions() {

        List<Question> questionList = questionRepository.findAll();

        return questionMapper.toResponseList(questionList);

    }

    public QuestionResponse getQuestionById(Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Not Found Question With Id: " + id));

        return questionMapper.toResponse(question);

    }

    @Transactional
    public QuestionResponse updateQuestion(QuestionRequest request, Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                                "Not Found Question With Id: " + id
                        ));

        question.setText(request.getText());
        question.setScore(request.getScore());

        return questionMapper.toResponse(question);
    }

    @Transactional
    public void deleteQuestionById(Long id) {

        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException(
                    "Not Found Question With Id: " + id
            );
        }

        questionRepository.deleteById(id);
    }


}
