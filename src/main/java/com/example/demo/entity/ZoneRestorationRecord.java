package com.example.demo.entity;



import jakarta.persistence.*;

import lombok.*;



import java.time.LocalDateTime;



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



private LocalDateTime restoredAt;



private Long eventId;



private String notes;

}