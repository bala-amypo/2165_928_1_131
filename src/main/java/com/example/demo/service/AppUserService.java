package com.example.demo.service;

import com.example.demo.entity.AppUser;

public interface AppUserService {

    AppUser register(String email, String password, String role);

    AppUser login(String email, String password);

    AppUser getByEmail(String email);
}
