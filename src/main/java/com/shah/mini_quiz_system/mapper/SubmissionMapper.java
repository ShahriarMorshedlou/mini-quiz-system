package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.Submission;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    @Mapping(source = "quiz.id", target = "quizId")
    SubmissionResponse toResponse(Submission submission);

    List<SubmissionResponse> toResponseList(List<Submission> submissionList);
}
