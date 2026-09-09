package com.shah.mini_quiz_system.controller;

import com.shah.mini_quiz_system.dto.request.QuestionRequest;
import com.shah.mini_quiz_system.dto.response.QuestionResponse;
import com.shah.mini_quiz_system.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<QuestionResponse> createQuestion (
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
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(){

        return ResponseEntity
                .ok()
                .body(questionService.getAllQuestions());
    }


}
