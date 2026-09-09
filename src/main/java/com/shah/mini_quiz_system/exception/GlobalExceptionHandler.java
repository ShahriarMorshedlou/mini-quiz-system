package com.shah.mini_quiz_system.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.shah.mini_quiz_system.dto.response.ErrorResponse;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                400,
                ex.getMessage(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);

    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    ResponseEntity<ErrorResponse> handleValidationException(
            HandlerMethodValidationException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getParameterValidationResults()
                .forEach(result -> {

                    String parameterName = result.getMethodParameter()
                            .getParameterName();

                    result.getResolvableErrors()
                            .forEach(error ->
                                    errors.put(
                                            parameterName,
                                            error.getDefaultMessage()
                                    )
                            );
                });

        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Validation failed",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }





    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                400,
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);


    }

    @ExceptionHandler(QuizNotFoundException.class)
    ResponseEntity<ErrorResponse> handleQuizNotFoundException (QuizNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                404,
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    ResponseEntity<ErrorResponse> handleQuestionNotFoundException (QuestionNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                404,
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }


}
