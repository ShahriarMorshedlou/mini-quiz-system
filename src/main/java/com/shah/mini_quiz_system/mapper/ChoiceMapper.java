package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.Choice;
import com.shah.mini_quiz_system.dto.response.ChoiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {

    @Mapping(source = "question.id", target = "questionId")
    ChoiceResponse toResponse(Choice choice);

    List<ChoiceResponse> toResponseList(List<Choice> choiceList);
}
