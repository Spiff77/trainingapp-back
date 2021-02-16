package com.treelevel.app.training.controller.exception.advice;

import com.treelevel.app.training.controller.exception.ProgramNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProgramNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ProgramNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(ProgramNotFoundException ex) {
        return ex.getMessage();
    }
}
