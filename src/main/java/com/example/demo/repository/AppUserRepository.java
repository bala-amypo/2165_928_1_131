package com.example.demo.repository;

import com.example.demo.entity.AppUser;
import java.util.Optional;

public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
    AppUser save(AppUser user);
}
