package hello.kpop.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminIndexController {

     @GetMapping
    public String index() {

        return "admin/main/index";
    }










}
