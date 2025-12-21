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

    private final DemandReadingRepository readingRepository;
    private final ZoneRepository zoneRepository;

    public DemandReadingServiceImpl(DemandReadingRepository readingRepository,
                                    ZoneRepository zoneRepository) {
        this.readingRepository = readingRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public DemandReading createReading(DemandReading reading) {

        Zone zone = zoneRepository.findById(reading.getZone().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        if (reading.getDemandMW() < 0) {
            throw new BadRequestException("demandMW must be >= 0");
        }

        if (reading.getRecordedAt().isAfter(Instant.now())) {
            throw new BadRequestException("recordedAt cannot be in the future");
        }

        reading.setZone(zone);
        return readingRepository.save(reading);
    }

    @Override
    public List<DemandReading> getReadingsForZone(Long zoneId) {

        zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        return readingRepository.findByZoneIdOrderByRecordedAtDesc(zoneId);
    }

    @Override
    public DemandReading getLatestReading(Long zoneId) {

        return readingRepository.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("No readings"));
    }

    @Override
    public List<DemandReading> getRecentReadings(Long zoneId, int limit) {

        List<DemandReading> readings =
                readingRepository.findByZoneIdOrderByRecordedAtDesc(zoneId);

        if (readings.size() <= limit) {
            return readings;
        }

        return readings.subList(0, limit);
    }
}
