package com.example.demo.entity;



import jakarta.persistence.*;

import lombok.*;



import java.time.LocalDateTime;



@Entity

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder

public class SupplyForecast {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @Column(nullable = false)

    private Double availableSupplyMW;



    private LocalDateTime forecastStart;

    private LocalDateTime forecastEnd;



    @Column(nullable = false, updatable = false)

    private LocalDateTime generatedAt;



    @PrePersist

    public void onCreate() {

        this.generatedAt = LocalDateTime.now();

        }

        }
