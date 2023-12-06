package com.statista.code.challenge.api.http.v1;

import com.statista.code.challenge.domain.exceptions.EntityNotFoundException;
import com.statista.code.challenge.infra.departments.DepartmentOperationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class contains all exception handling logic for the API.
 * It is responsible to translate thrown exceptions to well-structured API responses.
 * Each method annotated with `@ExceptionHandler` is responsible for handling related exceptions.
 **/
@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(RuntimeException exception, WebRequest request) {
        String message = exception.getMessage() != null ? exception.getMessage() : "";
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DepartmentOperationException.class)
    protected ResponseEntity<Object> handleDepartmentOperation(DepartmentOperationException exception, WebRequest request) {
        String message = exception.getMessage() != null ? exception.getMessage() : "";
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, message);
        body.setProperties(exception.getProperties());
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
}
