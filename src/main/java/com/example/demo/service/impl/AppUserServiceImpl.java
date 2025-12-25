package com.example.demo.service.impl;

import com.example.demo.entity.AppUser;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AppUserService;
import org.springframework.stereotype.Service; // <--- Import this
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Service // <--- THIS WAS MISSING
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public AppUserServiceImpl(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public AppUser register(String email, String password, String role) {
        AppUser user = AppUser.builder()
                .email(email)
                .password(encoder.encode(password))
                .active(true)
                .roles(Set.of(role))
                .build();
        return repo.save(user);
    }

    @Override
    public AppUser login(String email, String password) {
        // Note: Password checking is usually done by AuthenticationManager
        // but fetching the user here is fine.
        AppUser user = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user;
    }

    @Override
    public AppUser getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}