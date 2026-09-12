package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.AnswerRequest;
import com.shah.mini_quiz_system.dto.request.SubmissionRequest;
import com.shah.mini_quiz_system.dto.response.AnswerResponse;
import com.shah.mini_quiz_system.dto.response.SubmissionResponse;
import com.shah.mini_quiz_system.service.AnswerService;
import com.shah.mini_quiz_system.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/submission")
@Validated
public class SubmissionController {

    private final SubmissionService submissionService;
    private final AnswerService answerService;

    public SubmissionController(SubmissionService submissionService, AnswerService answerService) {
        this.submissionService = submissionService;
        this.answerService = answerService;
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

    @GetMapping
    @Operation(
            summary = "Get all submissions"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Submissions retrieved successfully"
    )
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions() {
        return ResponseEntity
                .ok()
                .body(submissionService.getAllSubmissions());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get submission by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Submission found successfully"
    )
    public ResponseEntity<SubmissionResponse> getSubmissionById(
            @PathVariable @Positive Long id
    ) {
        return ResponseEntity
                .ok()
                .body(submissionService.getSubmissionById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete submission by id"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Submission deleted successfully"
    )
    public ResponseEntity<Void> deleteSubmissionById(
            @PathVariable @Positive Long id
    ) {
        submissionService.deleteSubmissionById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update submission"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Submission updated successfully"
    )
    public ResponseEntity<SubmissionResponse> updateSubmission(
            @RequestBody @Valid SubmissionRequest request,
            @PathVariable @Positive Long id
    ) {
        return ResponseEntity
                .ok()
                .body(submissionService.updateSubmission(request, id));
    }

    @PostMapping("/{submissionId}/answers")
    public ResponseEntity<AnswerResponse> submitAnswer(
            @PathVariable @Positive Long submissionId,
            @RequestBody @Valid AnswerRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(answerService.submitAnswer(submissionId, request));
    }
}

