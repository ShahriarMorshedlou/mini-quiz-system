package com.shah.mini_quiz_system.controller;

import com.shah.mini_quiz_system.dto.request.AnswerRequest;
import com.shah.mini_quiz_system.dto.response.AnswerResponse;
import com.shah.mini_quiz_system.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/answer")
@Validated
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @Operation(
            summary = "Create answer"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Answer created successfully"
    )
    public ResponseEntity<AnswerResponse> createAnswer(
            @RequestBody
            @Valid
            AnswerRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(answerService.createAnswer(request));
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Get answer by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Answer found successfully"
    )
    public ResponseEntity<AnswerResponse> getAnswerById(
            @PathVariable
            @Positive
            Long id
    ) {
        return ResponseEntity
                .ok()
                .body(answerService.getAnswerById(id));
    }
}
