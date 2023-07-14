package com.example.simpleblog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice{

    @ExceptionHandler(Exception.class)
    public String handle500(RedirectAttributes redirectAttributes, Exception ex){
        log.info(ex);
        return "redirect:/board/list";
    }
}
