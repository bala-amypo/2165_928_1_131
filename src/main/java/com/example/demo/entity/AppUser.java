package com.example.demo.entity;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Long id;
    private String email;
    private String password;
    private Boolean active;
    private Set<String> roles;
}
