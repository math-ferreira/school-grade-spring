package com.school.grade.web.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = { ElementNotFoundException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ErrorResponse badRequest(ElementNotFoundException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ErrorResponse(NOT_FOUND.value(), ex.getMessage(), path);
    }

    @ExceptionHandler(value = { UnprocessableEntityException.class })
    @ResponseBody
    @ResponseStatus(code = UNPROCESSABLE_ENTITY)
    protected ErrorResponse unprocessableEntity(UnprocessableEntityException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return new ErrorResponse(UNPROCESSABLE_ENTITY.value(), ex.getMessage(), path);
    }

}
