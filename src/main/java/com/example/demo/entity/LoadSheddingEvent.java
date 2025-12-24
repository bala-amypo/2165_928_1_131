package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "load_shedding_events")
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
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @Column(nullable = false)
    private Instant eventStart;

    private Instant eventEnd;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private Long triggeredByForecastId;

    @Column(nullable = false)
    private Double expectedDemandReductionMW;
}
