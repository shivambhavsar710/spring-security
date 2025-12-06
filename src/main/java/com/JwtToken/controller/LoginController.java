package com.JwtToken.controller;

import com.JwtToken.model.Users;
import com.JwtToken.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return authService.login(user);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Users user){
        System.out.println(user);
        return authService.signUp(user);
    }

}
