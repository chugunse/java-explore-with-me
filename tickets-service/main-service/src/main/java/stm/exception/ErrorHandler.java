package stm.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import stm.exception.model.ApiError;
import stm.exception.model.ConflictException;
import stm.exception.model.ResourceNotFoundException;
import stm.util.Constants;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFoundException(final ResourceNotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("The required object was not found")
                .status("NOT_FOUND")
                .timestamp((LocalDateTime.now().format(Constants.formatterDateTime)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handlerValidate(final MethodArgumentNotValidException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Bad request")
                .status("Conflict")
                .timestamp((LocalDateTime.now().format(Constants.formatterDateTime)))
                .build();
    }

//    @ExceptionHandler
//    @ResponseStatus(code = HttpStatus.FORBIDDEN)
//    public ApiError handlerForbidden(final ForbiddenException e) {
//        return ApiError.builder()
//                .message(e.getMessage())
//                .reason("доступ запрещен")
//                .status("Forbidden")
//                .timestamp((LocalDateTime.now().format(Constants.formatter)))
//                .build();
//    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiError handleViolationException(final DataIntegrityViolationException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("доступ запрещен")
                .status("Forbidden")
                .timestamp((LocalDateTime.now().format(Constants.formatterDateTime)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleEmptyResultData(final EmptyResultDataAccessException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("The required object was not found")
                .status("NOT_FOUND")
                .timestamp((LocalDateTime.now().format(Constants.formatterDateTime)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Conflict")
                .status("Conflict")
                .timestamp((LocalDateTime.now().format(Constants.formatterDateTime)))
                .build();
    }

//    @ExceptionHandler
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    public ApiError handleBadRequestException(final BadRequestException e) {
//        return ApiError.builder()
//                .message(e.getMessage())
//                .reason("BadRequestException")
//                .status("BadRequestException")
//                .timestamp((LocalDateTime.now().format(Constants.formatter)))
//                .build();
//    }
}
