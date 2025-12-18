package com.example.demo.entity;



import jakarta.persistence.*;

import lombok.*;



import java.util.Set;



@Entity

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class AppUser {



@Id

@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long id;



@Column(nullable = false, unique = true)

private String email;



@Column(nullable = false)

private String password;



@ElementCollection(fetch = FetchType.EAGER)

private Set<String> roles;



private Boolean active;

}

