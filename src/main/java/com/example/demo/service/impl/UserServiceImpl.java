package com.example.demo.service;



import com.example.demo.entity.AppUser;

import com.example.demo.exception.ResourceNotFoundException;

import com.example.demo.repository.AppUserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;



@Service

public class UserServiceImpl implements UserService {



    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public UserServiceImpl(AppUserRepository userRepository,

    PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        }



        @Override

        public AppUser createUser(AppUser user) {



        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActive(true);



        return userRepository.save(user);

        }



        @Override

        public AppUser getUserByEmail(String email) {

            return userRepository.findByEmail(email)

            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            }

            }

            