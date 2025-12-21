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

    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(nullable = false)
    private Instant restoredAt;

    @Column(nullable = false)
    private Long eventId;

    private String notes;
}
