package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.DemandReadingService;

import java.time.Instant;
import java.util.*;

public class DemandReadingServiceImpl implements DemandReadingService {

    private final DemandReadingRepository readingRepo;
    private final ZoneRepository zoneRepo;

    public DemandReadingServiceImpl(DemandReadingRepository r, ZoneRepository z) {
        this.readingRepo = r;
        this.zoneRepo = z;
    }

    public DemandReading createReading(DemandReading r) {
        Zone z = zoneRepo.findById(r.getZone().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        if (r.getRecordedAt().isAfter(Instant.now()))
            throw new BadRequestException("future");

        if (r.getDemandMW() < 0)
            throw new BadRequestException(">= 0");

        r.setZone(z);
        return readingRepo.save(r);
    }

    public DemandReading getLatestReading(Long zoneId) {
        return readingRepo.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("No readings"));
    }

    public List<DemandReading> getReadingsForZone(Long zoneId) {
        zoneRepo.findById(zoneId).orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
        return readingRepo.findByZoneIdOrderByRecordedAtDesc(zoneId);
    }

    public List<DemandReading> getRecentReadings(Long zoneId, int limit) {
        List<DemandReading> all = getReadingsForZone(zoneId);
        return all.subList(0, Math.min(limit, all.size()));
    }
}
