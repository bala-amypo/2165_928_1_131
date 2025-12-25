package com.example.demo.repository;

import com.example.demo.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository; // Required for database magic
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
    // Spring generates the SQL for this automatically
    Optional<AppUser> findByEmail(String email);

    // Note: You do NOT need to define 'save()' manually. 
    // JpaRepository already provides it!
}