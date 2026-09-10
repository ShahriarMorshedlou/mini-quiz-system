package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.ChoiceRequest;
import com.shah.mini_quiz_system.dto.response.ChoiceResponse;
import com.shah.mini_quiz_system.service.ChoiceService;
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

    @GetMapping
    @Operation(
            summary = "Get all choices"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Choices retrieved successfully"
    )
    public ResponseEntity<List<ChoiceResponse>> getAllChoices() {
        return ResponseEntity
                .ok()
                .body(choiceService.getAllChoices());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get choice by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Choice found successfully"
    )
    public ResponseEntity<ChoiceResponse> getChoiceById(
            @PathVariable @Positive Long id
    ) {
        return ResponseEntity
                .ok()
                .body(choiceService.getChoiceById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete choice by id"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Choice deleted successfully"
    )
    public ResponseEntity<Void> deleteChoiceById(
            @PathVariable @Positive Long id
    ) {
        choiceService.deleteChoiceById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update choice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Choice updated successfully"
    )
    public ResponseEntity<ChoiceResponse> updateChoice(
            @RequestBody @Valid ChoiceRequest request,
            @PathVariable @Positive Long id
    ) {
        return ResponseEntity
                .ok()
                .body(choiceService.updateChoice(request, id));
    }


}
