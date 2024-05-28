package com.stm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<CustomErrorResponse> handleException(HttpStatus status, String defaultMessage, Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());

        CustomErrorResponse errorResponse = new CustomErrorResponse(defaultMessage, status.value(), errors);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CustomErrorResponse> handleNullPointerException(NullPointerException ex) {
        return handleException(HttpStatus.BAD_REQUEST, "Некорректный запрос", ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return handleException(HttpStatus.BAD_REQUEST, "Некорректные аргументы", ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        CustomErrorResponse errorResponse = new CustomErrorResponse("Некорректные аргументы", HttpStatus.BAD_REQUEST.value(), errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return handleException(HttpStatus.BAD_REQUEST, "Ошибка чтения HTTP-сообщения", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception ex) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера", ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return handleException(HttpStatus.FORBIDDEN, "Доступ запрещен", ex);
    }
}
