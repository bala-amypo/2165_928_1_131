package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    // 👇 This makes builder().role("ADMIN") work
    public static class AppUserBuilder {
        public AppUserBuilder role(String role) {
            this.roles = Set.of(role);
            return this;
        }
    }
}
