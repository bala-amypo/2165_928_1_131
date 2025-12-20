package com.example.demo.service.impl;

import com.example.demo.entity.DemandReading;
import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemandReadingRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.DemandReadingService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DemandReadingServiceImpl implements DemandReadingService {

    private final DemandReadingRepository demandRepo;
    private final ZoneRepository zoneRepo;

    // ✅ Constructor order REQUIRED by tests
    public DemandReadingServiceImpl(DemandReadingRepository demandRepo,
                                    ZoneRepository zoneRepo) {
        this.demandRepo = demandRepo;
        this.zoneRepo = zoneRepo;
    }

    // ---------------- CREATE READING ----------------
    @Override
    public DemandReading createReading(DemandReading reading) {

        // 🔴 Zone must exist
        Zone zone = zoneRepo.findById(reading.getZone().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );

        // 🔴 Negative demand validation
        if (reading.getDemandMW() < 0) {
            throw new BadRequestException("Demand must be >= 0");
        }

        // 🔴 Future timestamp validation
        if (reading.getRecordedAt().isAfter(Instant.now())) {
            throw new BadRequestException("Reading time is in the future");
        }

        reading.setZone(zone);
        return demandRepo.save(reading);
    }

    // ---------------- GET READINGS FOR ZONE ----------------
    @Override
    public List<DemandReading> getReadingsForZone(Long zoneId) {

        // 🔴 Zone must exist
        zoneRepo.findById(zoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );

        return demandRepo.findByZoneIdOrderByRecordedAtDesc(zoneId);
    }

    // ---------------- GET LATEST READING ----------------
    @Override
    public DemandReading getLatestReading(Long zoneId) {

        // 🔴 Zone must exist
        zoneRepo.findById(zoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );

        return demandRepo.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No readings")
                );
    }

    // ---------------
