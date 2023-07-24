package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.model.*;
import util.Constants;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFoundException(final ResourceNotFoundException e) {
        ApiError apiError = ApiError.builder()
//                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .message(e.getMessage())
                .reason("The required object was not found")
                .status("NOT_FOUND")
                .timestamp((LocalDateTime.now().format(Constants.formatter)))
                .build();
        log.warn(e.getMessage());
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handlerValidate(final MethodArgumentNotValidException e) {
        ApiError apiError = ApiError.builder()
//                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .message(e.getMessage())
                .reason("Bad request")
                .status("Conflict")
                .timestamp((LocalDateTime.now().format(Constants.formatter)))
                .build();
        log.warn(e.getLocalizedMessage());
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ApiError handlerForbidden(final ForbiddenException e) {
        ApiError apiError = ApiError.builder()
//                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .message(e.getMessage())
                .reason("доступ запрещен")
                .status("Forbidden")
                .timestamp((LocalDateTime.now().format(Constants.formatter)))
                .build();
        log.warn(e.getMessage());
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiError handleViolationException(final DataIntegrityViolationException e) {
        ApiError apiError = ApiError.builder()
//                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .message(e.getMessage())
                .reason("доступ запрещен")
                .status("Forbidden")
                .timestamp((LocalDateTime.now().format(Constants.formatter)))
                .build();
        log.warn(e.getMessage());
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        ApiError apiError = ApiError.builder()
//                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .message(e.getMessage())
                .reason("Conflict")
                .status("Conflict")
                .timestamp((LocalDateTime.now().format(Constants.formatter)))
                .build();
        log.warn(e.getMessage());
        return apiError;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        ApiError apiError = ApiError.builder()
//                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .message(e.getMessage())
                .reason("BadRequestException")
                .status("BadRequestException")
                .timestamp((LocalDateTime.now().format(Constants.formatter)))
                .build();
        log.warn(e.getMessage());
        return apiError;
    }
}
