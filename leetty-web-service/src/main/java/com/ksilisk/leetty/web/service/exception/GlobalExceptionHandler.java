package com.ksilisk.leetty.web.service.exception;

import com.ksilisk.leetty.web.service.exception.type.EntityAlreadyExistsException;
import com.ksilisk.leetty.web.service.exception.type.EntityNotFoundException;
import com.ksilisk.leetty.web.service.exception.type.QuestionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(Exception ex, WebRequest webRequest) {
        log.warn("Handled entity not found exception. Request Details: {}", webRequest.getDescription(true), ex);
        ProblemDetail body = createProblemDetail(ex, HttpStatus.NOT_FOUND, ex.getMessage(), null, null, webRequest);
        return createResponseEntity(body, null, HttpStatus.NOT_FOUND, webRequest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Object> handleQuestionNotFound(Exception ex, WebRequest webRequest) {
        log.warn("Handled question not found exception. Request Details: {}", webRequest.getDescription(true), ex);
        ProblemDetail body = createProblemDetail(ex, HttpStatus.NOT_FOUND, ex.getMessage(), null, null, webRequest);
        return createResponseEntity(body, null, HttpStatus.NOT_FOUND, webRequest);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExists(Exception ex, WebRequest webRequest) {
        log.warn("Handled entity already exists exception. Request Details: {}", webRequest.getDescription(true), ex);
        ProblemDetail body = createProblemDetail(ex, HttpStatus.CONFLICT, ex.getMessage(), null, null, webRequest);
        return createResponseEntity(body, null, HttpStatus.CONFLICT, webRequest);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(LeettyWebServiceException.class)
    public ResponseEntity<Object> handleLeettyWebServiceException(Exception ex, WebRequest webRequest) {
        log.warn("Handled leettyWebServiceException. Request Details: {}", webRequest.getDescription(true), ex);
        ProblemDetail body = createProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null, null, webRequest);
        return createResponseEntity(body, null, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}
