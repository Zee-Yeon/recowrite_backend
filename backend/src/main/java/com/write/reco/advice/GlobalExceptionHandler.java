package com.write.reco.advice;

import com.write.reco.advice.exception.CustomException;
import com.write.reco.advice.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<?> handleCustomException(CustomException exception) {
        return new ResponseEntity<>(Response.create(exception.getResponseCode(), exception.getContent()),
                exception.getResponseCode().getHttpStatus());

    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
