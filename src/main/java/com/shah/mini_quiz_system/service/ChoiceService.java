package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Choice;
import com.shah.mini_quiz_system.domain.Question;
import com.shah.mini_quiz_system.dto.request.ChoiceRequest;
import com.shah.mini_quiz_system.dto.response.ChoiceResponse;
import com.shah.mini_quiz_system.dto.response.QuestionResponse;
import com.shah.mini_quiz_system.exception.QuestionNotFoundException;
import com.shah.mini_quiz_system.mapper.ChoiceMapper;
import com.shah.mini_quiz_system.repoditory.ChoiceRepository;
import com.shah.mini_quiz_system.repoditory.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {

    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceMapper choiceMapper;

    public ChoiceService(ChoiceRepository choiceRepository, ChoiceMapper choiceMapper, QuestionRepository questionRepository) {
        this.choiceRepository = choiceRepository;
        this.choiceMapper = choiceMapper;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public ChoiceResponse createChoice(ChoiceRequest request) {

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                                "Not Found Question With Id: " + request.getQuestionId()
                        ));

        Choice choice = new Choice(
                request.getText(),
                request.isCorrect()
        );

        choice.setQuestion(question);

        Choice savedChoice = choiceRepository.save(choice);

        return choiceMapper.toResponse(savedChoice);
    }
}
