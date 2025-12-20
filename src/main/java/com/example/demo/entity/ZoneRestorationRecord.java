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
public class ZoneRestorationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Relationship (REQUIRED)
    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    // 🔴 MUST be Instant
    @Column(nullable = false)
    private Instant restoredAt;

    @Column(nullable = false)
    private Long eventId;

    private String notes;
}
