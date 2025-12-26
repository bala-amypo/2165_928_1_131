package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "load_shedding_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadSheddingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone_id", nullable = false)
    private Long zoneId;

    private Double expectedDemandReductionMW;

    private Instant eventStart;

    private String reason;
}
