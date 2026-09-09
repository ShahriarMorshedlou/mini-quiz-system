package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.QuizRequest;
import com.shah.mini_quiz_system.dto.response.QuizResponse;
import com.shah.mini_quiz_system.service.QuizService;
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
@RequestMapping("/quiz")
@Validated
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @Operation(
            summary = "Create a new quiz"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Quiz created successfully"
    )
    @PostMapping
    public ResponseEntity<QuizResponse> createQuiz(
            @RequestBody
            @Valid
            QuizRequest request
    ) {


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(quizService.createQuiz(request));

    }


    @Operation(
            summary = "Get All Quizzes"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Get All Quizzes Successfully"

    )
    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {

        return ResponseEntity
                .ok()
                .body(quizService.getAllQuizzes());

    }

    @Operation(
            summary = "Get quiz by ID"

    )
    @ApiResponse(
            responseCode = "200",
            description = "Quiz retrieved successfully")

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(
            @PathVariable
            Long id
    ) {
        return ResponseEntity
                .ok()
                .body(quizService.getQuizById(id));
    }


    @Operation(
            summary = "Update quiz"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Quiz updated successfully"
    )
    @PutMapping("/{id}")
    public ResponseEntity<QuizResponse> updateQuiz(
            @RequestBody
            @Valid
            QuizRequest request,
            @Positive
            @PathVariable
            Long id
    ) {
        return ResponseEntity
                .ok()
                .body(quizService.updateQuiz(request, id));
    }

    @Operation(
            summary = "Delete quiz"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Quiz Deleted successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizById(
            @PathVariable
            @Positive
            Long id) {

        quizService.deleteQuizById(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
