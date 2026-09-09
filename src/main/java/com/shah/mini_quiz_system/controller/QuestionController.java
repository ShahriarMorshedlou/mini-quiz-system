package com.shah.mini_quiz_system.controller;

import com.shah.mini_quiz_system.dto.request.QuestionRequest;
import com.shah.mini_quiz_system.dto.response.QuestionResponse;
import com.shah.mini_quiz_system.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@Validated
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @Operation(
            summary = "Create question"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Create question successfully"
    )
    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @RequestBody
            @Valid
            QuestionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(questionService.createQuestion(request));
    }


    @Operation(
            summary = "Get all questions"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Get all question successfully"
    )
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions() {

        return ResponseEntity
                .ok()
                .body(questionService.getAllQuestions());
    }


    @Operation(
            summary = "Get question by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Get question by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(
            @PathVariable
            @Positive
            Long id) {

        return ResponseEntity
                .ok()
                .body(questionService.getQuestionById(id));
    }


    @Operation(
            summary = "Update question"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Question updated successfully"
    )
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @RequestBody
            @Valid
            QuestionRequest request,

            @PathVariable
            @Positive
            Long id
    ) {
        return ResponseEntity
                .ok()
                .body(questionService.updateQuestion(request, id));
    }

    @Operation(
            summary = "Delete question"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Question deleted successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionById(
            @PathVariable
            @Positive
            Long id
    ) {
        questionService.deleteQuestionById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
