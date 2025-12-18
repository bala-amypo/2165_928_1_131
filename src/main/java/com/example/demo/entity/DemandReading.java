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

public class DemandReading {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @ManyToOne(optional = false)

    @JoinColumn(name = "zone_id")

    private Zone zone;



    @Column(nullable = false)

    private Double demandMW;



    @Column(nullable = false)

    private LocalDateTime recordedAt;

    }

    