package com.epam.recreation_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class APIExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView notFoundException(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.setViewName("exception");
        modelAndView.addObject("status", HttpStatus.NOT_FOUND.toString());
        modelAndView.addObject("exception", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ModelAndView forbiddenException(ForbiddenException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.FORBIDDEN);
        modelAndView.setViewName("exception");
        modelAndView.addObject("status", HttpStatus.FORBIDDEN.toString());
        modelAndView.addObject("exception", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = {RestException.class})
    public ModelAndView restException(RestException exception) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(exception.getHttpStatus());
        modelAndView.setViewName("exception");
        modelAndView.addObject("exception", exception.getMessage());
        modelAndView.addObject("status", exception.getHttpStatus());
        return modelAndView;
    }
}
