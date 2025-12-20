package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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

    // ✅ Relationship (REQUIRED)
    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    // 🔴 MUST be Instant
    @Column(nullable = false)
    private Instant eventStart;

    // Optional (can be null initially)
    private Instant eventEnd;

    private String reason;

    @Column(nullable = false)
    private Long triggeredByForecastId;

    @Column(nullable = false)
    private Double expectedDemandReductionMW;
}
