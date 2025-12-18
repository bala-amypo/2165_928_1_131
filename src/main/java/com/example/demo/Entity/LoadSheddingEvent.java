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

public class LoadSheddingEvent {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    @ManyToOne(optional = false)

    @JoinColumn(name = "zone_id")

    private Zone zone;



    private LocalDateTime eventStart;

    private LocalDateTime eventEnd;



    private String reason;



    private Long triggeredByForecastId;



    private Double expectedDemandReductionMW;

    }
    


