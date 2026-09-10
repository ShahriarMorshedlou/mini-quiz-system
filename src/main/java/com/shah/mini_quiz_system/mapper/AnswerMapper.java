package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.Answer;
import com.shah.mini_quiz_system.dto.response.AnswerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(source = "choice.id", target = "choiceId")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "submission.id", target = "submissionId")
    AnswerResponse toResponse(Answer answer);
    List<AnswerResponse> toResponseList(List<Answer> answerList);


}
