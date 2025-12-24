package com.example.demo.entity;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadSheddingEvent {

    private Long id;
    private Long zoneId;
    private Double expectedDemandReductionMW;
    private Instant eventStart;
    private String reason;
}
