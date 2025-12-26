package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "demand_readings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private Double demandMW;

    private Instant recordedAt;
}
