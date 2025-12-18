package com.example.demo.entity;



import jakarta.persistence.*;

import lombok.*;



import java.time.LocalDateTime;



@Entity

@Table(

    name = "zones",

    uniqueConstraints = @UniqueConstraint(columnNames = "zone_name")

    )

    @Getter

    @Setter

    @NoArgsConstructor

    @AllArgsConstructor

    @Builder

    public class Zone {



        @Id

        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long id;



        @Column(name = "zone_name", nullable = false, unique = true)

        private String zoneName;



        @Column(nullable = false)

        private Integer priorityLevel;



        private Integer population;



        @Column(nullable = false)

        private Boolean active;



        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;



        @PrePersist

        public void onCreate() {

            this.createdAt = LocalDateTime.now();

            this.active = true;

            }



            @PreUpdate

            public void onUpdate() {

                this.updatedAt = LocalDateTime.now();

                }

                }


            
        
    
