package com.paterni.appointment.web.resources.exceptions;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paterni.appointment.domain.services.exceptions.BusinessException;
import com.paterni.appointment.domain.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<StandardError> dateTimeParseException(DateTimeParseException exception,
            HttpServletRequest request) {

        StandardError error = new StandardError();

        HttpStatus status = HttpStatus.NOT_FOUND;

        error.setError("Parse Date Exception");
        error.setMessage("The date format is not valid. Use 'yyyy-MM-dd'");
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrors> validationException(MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        ValidationErrors error = new ValidationErrors();

        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        error.setError("Validation Error");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(e -> error.addError(e.getDefaultMessage()));

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException exception, HttpServletRequest request) {

        StandardError error = new StandardError();

        HttpStatus status = HttpStatus.BAD_REQUEST;

        error.setError("Database exception");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> bussinessException(BusinessException exception, HttpServletRequest request) {

        StandardError error = new StandardError();

        HttpStatus status = HttpStatus.CONFLICT;

        error.setError("Business exception");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundException(EntityNotFoundException exception,
            HttpServletRequest request) {

        StandardError error = new StandardError();

        HttpStatus status = HttpStatus.NOT_FOUND;

        error.setError("Resource not found");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setStatus(status.value());
        error.setTimeStamp(Instant.now());

        return ResponseEntity.status(status).body(error);
    }

}