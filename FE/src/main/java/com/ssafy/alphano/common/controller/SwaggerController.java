package com.ssafy.alphano.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class SwaggerController {

    @GetMapping("/swagger/")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}
