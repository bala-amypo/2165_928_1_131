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

    @ManyToOne(optional = false)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(nullable = false)
    private Instant eventStart;

    private Instant eventEnd;

    private String reason;

    private Long triggeredByForecastId;

    @Column(nullable = false)
    private Double expectedDemandReductionMW;
}
