package com.mysite.sbbmission.global.exceptions.handler;

import com.mysite.sbbmission.global.exceptions.DataNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public String handleDataNotFoundException(DataNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error/data_not_found";
    }
}
