package com.example.demo.service;

import com.example.demo.entity.DemandReading;
import com.example.demo.repository.DemandReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DemandReadingServiceImpl implements DemandReadingService {

    private final DemandReadingRepository demandRepo;

    public DemandReadingServiceImpl(DemandReadingRepository demandRepo) {
        this.demandRepo = demandRepo;
    }

    @Override
    public DemandReading createReading(DemandReading reading) {

        if (reading.getDemandMW() < 0) {
            throw new IllegalArgumentException("Demand must be >= 0");
        }

        if (reading.getRecordedAt().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reading cannot be future");
        }

        return demandRepo.save(reading);
    }

    @Override
    public List<DemandReading> getReadingsForZone(Long zoneId) {
        return demandRepo.findByZoneIdOrderByRecordedAtDesc(zoneId);
    }

    @Override
    public DemandReading getLatestReading(Long zoneId) {
        return demandRepo.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)
                .orElseThrow(() -> new IllegalArgumentException("No readings found"));
    }

    @Override
    public List<DemandReading> getRecentReadings(Long zoneId, int limit) {
        return demandRepo.findByZoneIdOrderByRecordedAtDesc(zoneId)
                .stream()
                .limit(limit)
                .toList();
    }
}
