package com.hainguyen.carrental.controller;

import com.hainguyen.carrental.exception.ObjectNotFound;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("/")
    public String goHome() {
        return "/notFound";
    }

    @GetMapping("/login")
    public String goLogin() {
        return "/login";
    }

    @GetMapping("/register")
    public String goResgister() {
        return "/register";
    }

    @ExceptionHandler(ObjectNotFound.class)
    public ModelAndView showNotFoundPage() {
        return new ModelAndView("notFound");
    }
}
