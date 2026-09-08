package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.Quiz;
import com.shah.mini_quiz_system.dto.response.QuizResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface QuizMapper {

    QuizResponse toResponse(Quiz quiz);

    List<QuizResponse> toResponseList(List<Quiz> quizList);
}
