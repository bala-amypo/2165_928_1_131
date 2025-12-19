package com.example.demo.service;

import com.example.demo.entity.AppUser;

public interface UserService {

    AppUser createUser(AppUser user);

    AppUser getUserByEmail(String email);
}
