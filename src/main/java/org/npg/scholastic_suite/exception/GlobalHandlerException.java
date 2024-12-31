package org.npg.scholastic_suite.exception;

import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.dto.AppApiErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {
    @Override
    @Nonnull
    protected ResponseEntity<Object> handleNoResourceFoundException(@Nonnull NoResourceFoundException ex, @Nonnull HttpHeaders headers, @Nonnull HttpStatusCode status, @Nonnull WebRequest request) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, @Nonnull WebRequest request) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(@Nonnull DataIntegrityViolationException ex, @Nonnull WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                AppApiErrorResponse.build(HttpStatus.CONFLICT, ErrorMessages.ALREADY_EXISTS, getPath(request), null)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(@Nonnull ConstraintViolationException ex, @Nonnull WebRequest request) {
        Set<String> errors = new HashSet<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath().toString() + ":" + violation.getMessage());
        }
        return ResponseEntity.badRequest().body(AppApiErrorResponse.build(HttpStatus.BAD_REQUEST, ErrorMessages.VALIDATION_FAILED, getPath(request), errors));
    }

    private String getPath(final WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
