package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class ZoneDTO {

    private Long id;

    @NotBlank
    @Schema(description = "Unique name of the zone", example = "Zone-A")
    private String zoneName;

    @Min(value = 1, message = "priorityLevel must be >= 1")
    @Schema(description = "Priority level (minimum 1)", example = "1")
    private Integer priorityLevel;

    @Schema(description = "Population of the zone", example = "25000")
    private Integer population;

    private Boolean active;

    private Instant createdAt;
    private Instant updatedAt;
}
