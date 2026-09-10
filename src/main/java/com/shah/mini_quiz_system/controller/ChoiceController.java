package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.ChoiceRequest;
import com.shah.mini_quiz_system.dto.response.ChoiceResponse;
import com.shah.mini_quiz_system.service.ChoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/choice")
@Validated
public class ChoiceController {
    private final ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping
    @Operation(
            summary = "Create choice"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Choice created successfully"
    )
    public ResponseEntity<ChoiceResponse> createChoice(
            @RequestBody
            @Valid
            ChoiceRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(choiceService.createChoice(request));
    }
}
