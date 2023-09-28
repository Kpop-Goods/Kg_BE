package com.example.kpopbe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/main")
    public String main() {
        return "k-pop-main";
    }
}
