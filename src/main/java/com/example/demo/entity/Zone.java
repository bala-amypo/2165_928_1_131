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
@Table(
    name = "zones",
    uniqueConstraints = @UniqueConstraint(columnNames = "zoneName")
)
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String zoneName;

    @Column(nullable = false)
    private Integer priorityLevel;

    private Integer population;

    @Column(nullable = false)
    private Boolean active = true;

    private Instant createdAt;
    private Instant updatedAt;

    // 🔴 AUTOMATIC TIMESTAMP HANDLING

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
