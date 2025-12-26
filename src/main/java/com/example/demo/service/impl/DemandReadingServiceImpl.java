package com.example.demo.service.impl;

import com.example.demo.entity.DemandReading;
import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException; // Ensure you have this exception class
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemandReadingRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.DemandReadingService;
import org.springframework.stereotype.Service; // <--- This annotation is CRITICAL

import java.time.Instant;
import java.util.List;

@Service // <--- Missing this caused the "Bean not found" error
public class DemandReadingServiceImpl implements DemandReadingService {

    private final DemandReadingRepository readingRepo;
    private final ZoneRepository zoneRepo;

    public DemandReadingServiceImpl(DemandReadingRepository r, ZoneRepository z) {
        this.readingRepo = r;
        this.zoneRepo = z;
    }

    // @Override ensures this matches your Interface
    @Override
    public DemandReading createReading(DemandReading r) {
        Zone z = zoneRepo.findById(r.getZone().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        if (r.getRecordedAt() != null && r.getRecordedAt().isAfter(Instant.now()))
            throw new BadRequestException("Reading time cannot be in the future");

        if (r.getDemandMW() < 0)
            throw new BadRequestException("Demand must be >= 0");

        r.setZone(z);
        return readingRepo.save(r);
    }

    @Override
    public DemandReading getLatestReading(Long zoneId) {
        return readingRepo.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("No readings found for this zone"));
    }

    @Override
    public List<DemandReading> getReadingsForZone(Long zoneId) {
        if (!zoneRepo.existsById(zoneId)) {
            throw new ResourceNotFoundException("Zone not found");
        }
        return readingRepo.findByZoneIdOrderByRecordedAtDesc(zoneId);
    }

    @Override
    public List<DemandReading> getRecentReadings(Long zoneId, int limit) {
        List<DemandReading> all = getReadingsForZone(zoneId);
        return all.subList(0, Math.min(limit, all.size()));
    }
}