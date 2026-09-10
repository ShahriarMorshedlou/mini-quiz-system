package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.SubmissionRequest;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import com.shah.mini_quiz_system.service.SubmissionService;
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
@RequestMapping("/submission")
@Validated
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    @Operation(
            summary = "Create submission"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Submission created successfully"
    )
    public ResponseEntity<SubmissionResponse> createSubmission(
            @RequestBody @Valid SubmissionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(submissionService.createSubmission(request));
    }
}

