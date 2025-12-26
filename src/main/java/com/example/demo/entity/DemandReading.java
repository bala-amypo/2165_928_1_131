package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demand_readings")
public class DemandReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double demand;

    private String zone;

    public DemandReading() {}

    public DemandReading(Double demand, String zone) {
        this.demand = demand;
        this.zone = zone;
    }

    public Long getId() {
        return id;
    }

    public Double getDemand() {
        return demand;
    }

    public void setDemand(Double demand) {
        this.demand = demand;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
