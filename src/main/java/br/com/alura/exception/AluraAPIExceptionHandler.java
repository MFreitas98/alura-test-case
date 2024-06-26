package br.com.alura.exception;


import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ResponseBody
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class AluraAPIExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({UserNotFoundException.class, NPSDataNotFoundException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorMessage handleUserNotFoundException(final Exception ex, WebRequest request) {
        return handleError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler({ConstraintViolationException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDataBaseExceptions(WebRequest request) {
        return handleError(HttpStatus.BAD_REQUEST, "Data used on request is not correct.", request.getDescription(false));
    }

    @ExceptionHandler({UnprocessableEntityException.class, CourseNotRegisteredException.class, UserNotRegisteredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleUnprocessableEntityAndCourseNotRegisteredExceptions(final Exception ex, WebRequest request) {
        return handleError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
    }

    private ErrorMessage handleError(HttpStatus status, String errorMessage, String requestDescription) {
        return ErrorMessage.builder().statusCode(status.value()).timestamp(LocalDateTime.now()).message(errorMessage).description(requestDescription).build();
    }
}
