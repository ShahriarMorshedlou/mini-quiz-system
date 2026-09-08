package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.QuizRequest;
import com.shah.mini_quiz_system.dto.response.QuizResponse;
import com.shah.mini_quiz_system.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
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
}
