package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Answer;
import com.shah.mini_quiz_system.domain.Choice;
import com.shah.mini_quiz_system.domain.Question;
import com.shah.mini_quiz_system.domain.Submission;
import com.shah.mini_quiz_system.dto.request.AnswerRequest;
import com.shah.mini_quiz_system.dto.response.AnswerResponse;
import com.shah.mini_quiz_system.exception.AnswerNotFoundException;
import com.shah.mini_quiz_system.exception.ChoiceNotFoundException;
import com.shah.mini_quiz_system.exception.QuestionNotFoundException;
import com.shah.mini_quiz_system.exception.SubmissionNotFoundException;
import com.shah.mini_quiz_system.mapper.AnswerMapper;
import com.shah.mini_quiz_system.repoditory.AnswerRepository;
import com.shah.mini_quiz_system.repoditory.ChoiceRepository;
import com.shah.mini_quiz_system.repoditory.QuestionRepository;
import com.shah.mini_quiz_system.repoditory.SubmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;

    public AnswerService(AnswerRepository answerRepository,
                         AnswerMapper answerMapper,
                         ChoiceRepository choiceRepository,
                         QuestionRepository questionRepository,
                         SubmissionRepository submissionRepository) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.submissionRepository = submissionRepository;
    }

    @Transactional
    public AnswerResponse createAnswer(AnswerRequest request) {

        Choice choice = choiceRepository.findById(request.getChoiceId())
                .orElseThrow(() -> new ChoiceNotFoundException("Not Found Choice With Id: " + request.getChoiceId()));

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Not Found Question With Id: " + request.getQuestionId()));

        Submission submission = submissionRepository.findById(request.getSubmissionId())
                .orElseThrow(() -> new SubmissionNotFoundException("Not Found Submission With Id: " + request.getSubmissionId()));


        Answer answer = new Answer(
                choice,
                question,
                submission
        );


        Answer savedAnswer = answerRepository.save(answer);

        return answerMapper.toResponse(savedAnswer);
    }

    public AnswerResponse getAnswerById(Long id) {

        Answer answer = answerRepository.findById(id)
                .orElseThrow(() ->
                        new AnswerNotFoundException(
                                "Not Found Answer With Id: " + id
                        ));

        return answerMapper.toResponse(answer);
    }

    public List<AnswerResponse> getAllAnswers() {

        List<Answer> answerList = answerRepository.findAll();

        return answerMapper.toResponseList(answerList);
    }

}
