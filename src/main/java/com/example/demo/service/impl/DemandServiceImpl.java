package com.example.demo.service;



import com.example.demo.entity.DemandReading;

import com.example.demo.repository.DemandReadingRepository;

import org.springframework.stereotype.Service;



import java.time.LocalDateTime;



@Service

public class DemandServiceImpl implements DemandService {



    private final DemandReadingRepository demandRepo;



    public DemandServiceImpl(DemandReadingRepository demandRepo) {

        this.demandRepo = demandRepo;

        }



        @Override

        public DemandReading addDemandReading(DemandReading reading) {



        if (reading.getDemandMW() < 0) {

        throw new IllegalArgumentException("Demand cannot be negative");

        }



        if (reading.getRecordedAt().isAfter(LocalDateTime.now())) {

        throw new IllegalArgumentException("Recorded time cannot be in future");

        }



        return demandRepo.save(reading);

        }



        @Override

        public DemandReading getLatestDemandForZone(Long zoneId) {

        return demandRepo.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)

        .orElseThrow(() -> new IllegalArgumentException("No demand data found"));

        }

        }

        
    }
}