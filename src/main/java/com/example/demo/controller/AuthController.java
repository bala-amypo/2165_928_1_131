package com.example.demo.controller;

import com.example.demo.entity.AppUser;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AppUser user) {
        AppUser dbUser = userService.getUserByEmail(user.getEmail());
        String token = jwtUtil.generateToken(dbUser.getEmail());
        return Map.of("token", token);
    }
}
