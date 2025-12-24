package com.example.demo.service;

import com.example.demo.entity.AppUser;

public interface AppUserService {
    AppUser createUser(String email, String password);
    AppUser getByEmail(String email);
}
