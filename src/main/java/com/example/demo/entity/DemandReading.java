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
public class DemandReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Relationship (CORRECT)
    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(nullable = false)
    private Double demandMW;

    // 🔴 MUST BE Instant (NOT LocalDateTime)
    @Column(nullable = false)
    private Instant recordedAt;
}
