package com.example.demo.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Long id;
    private String email;
    private String role;
}
