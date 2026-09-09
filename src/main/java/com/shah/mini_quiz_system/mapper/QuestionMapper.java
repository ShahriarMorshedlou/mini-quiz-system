package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.Question;
import com.shah.mini_quiz_system.dto.response.QuestionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionResponse toResponse(Question question);

    List<QuestionResponse> toResponseList(List<Question> questionList);

}
