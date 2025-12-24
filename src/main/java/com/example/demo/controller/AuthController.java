package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.AppUser;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AppUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AppUserService appUserService, JwtTokenProvider jwtTokenProvider) {
        this.appUserService = appUserService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        AppUser user = appUserService.register(
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        String token = jwtTokenProvider.createToken(user);
        return new AuthResponse(user.getEmail(), user.getRoles().iterator().next(), token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        AppUser user = appUserService.login(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtTokenProvider.createToken(user);
        return new AuthResponse(user.getEmail(), user.getRoles().iterator().next(), token);
    }
}
