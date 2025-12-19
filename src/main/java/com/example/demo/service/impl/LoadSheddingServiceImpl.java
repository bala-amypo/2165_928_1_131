package com.example.demo.service;

import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.entity.Zone;
import com.example.demo.repository.LoadSheddingEventRepository;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoadSheddingServiceImpl implements LoadSheddingService {

    private final SupplyForecastRepository forecastRepo;
    private final ZoneRepository zoneRepo;
    private final LoadSheddingEventRepository eventRepo;

    public LoadSheddingServiceImpl(
            SupplyForecastRepository forecastRepo,
            ZoneRepository zoneRepo,
            LoadSheddingEventRepository eventRepo) {

        this.forecastRepo = forecastRepo;
        this.zoneRepo = zoneRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public void triggerLoadShedding(Long forecastId) {

        var forecast = forecastRepo.findById(forecastId)
                .orElseThrow(() -> new IllegalArgumentException("Forecast not found"));

        List<Zone> zones =
                zoneRepo.findByActiveTrueOrderByPriorityLevelAsc();

        if (zones.isEmpty()) {
            throw new IllegalArgumentException("No zones available");
        }

        for (Zone zone : zones) {

            LoadSheddingEvent event = LoadSheddingEvent.builder()
                    .zone(zone)
                    .eventStart(LocalDateTime.now())
                    .reason("Overload")
                    .triggeredByForecastId(forecastId)
                    .expectedDemandReductionMW(0.0)
                    .build();

            eventRepo.save(event);
            zone.setActive(false);
            zoneRepo.save(zone);
        }
    }

    @Override
    public LoadSheddingEvent getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    @Override
    public List<LoadSheddingEvent> getEventsForZone(Long zoneId) {
        return eventRepo.findByZoneIdOrderByEventStartDesc(zoneId);
    }

    @Override
    public List<LoadSheddingEvent> getAllEvents() {
        return eventRepo.findAll();
    }
}
