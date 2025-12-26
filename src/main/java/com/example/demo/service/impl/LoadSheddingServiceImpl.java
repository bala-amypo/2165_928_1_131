package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.LoadSheddingService;

import org.springframework.stereotype.Service;   // ✅ add this

import java.time.Instant;
import java.util.*;

@Service   // ✅ add this
public class LoadSheddingServiceImpl implements LoadSheddingService {

    private final SupplyForecastRepository forecastRepo;
    private final ZoneRepository zoneRepo;
    private final DemandReadingRepository readingRepo;
    private final LoadSheddingEventRepository eventRepo;

    public LoadSheddingServiceImpl(SupplyForecastRepository f, ZoneRepository z,
                                  DemandReadingRepository r, LoadSheddingEventRepository e) {
        this.forecastRepo = f;
        this.zoneRepo = z;
        this.readingRepo = r;
        this.eventRepo = e;
    }

    @Override
    public LoadSheddingEvent triggerLoadShedding(Long forecastId) {
        SupplyForecast f = forecastRepo.findById(forecastId)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        List<Zone> zones = zoneRepo.findByActiveTrueOrderByPriorityLevelAsc();
        if (zones.isEmpty()) throw new BadRequestException("No overload");

        for (Zone z : zones) {
            Optional<DemandReading> dr = readingRepo.findFirstByZoneIdOrderByRecordedAtDesc(z.getId());
            if (dr.isPresent() && dr.get().getDemandMW() > f.getAvailableSupplyMW()) {
                LoadSheddingEvent ev = LoadSheddingEvent.builder()
                        .zone(z)
                        .expectedDemandReductionMW(dr.get().getDemandMW())
                        .eventStart(Instant.now())
                        .reason("Auto shedding")
                        .build();
                return eventRepo.save(ev);
            }
        }

        throw new BadRequestException("No overload");
    }

    @Override
    public LoadSheddingEvent getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    public List<LoadSheddingEvent> getAllEvents() {
        return eventRepo.findAll();
    }

    @Override
    public List<LoadSheddingEvent> getEventsForZone(Long zoneId) {
        return eventRepo.findByZoneIdOrderByEventStartDesc(zoneId);
    }
}
