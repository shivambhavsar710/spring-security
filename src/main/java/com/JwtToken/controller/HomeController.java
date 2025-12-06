package com.JwtToken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/public")
    public String home() {
        return "Welcome to Spring Security";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Welcome admin";
    }

    @GetMapping("/user")
    public String user(){
        return "Welcome user";
    }

}
