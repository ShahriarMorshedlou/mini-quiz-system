package com.shah.mini_quiz_system.controller;


import com.shah.mini_quiz_system.dto.request.RegisterRequest;
import com.shah.mini_quiz_system.dto.response.RegisterResponse;
import com.shah.mini_quiz_system.service.AuthService;
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
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;



    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Register a new user")
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully"
    )
    @PostMapping
    public ResponseEntity<RegisterResponse> register(
            @RequestBody
            @Valid
            RegisterRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }
}
