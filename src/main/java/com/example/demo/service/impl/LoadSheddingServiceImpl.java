package com.example.demo.service.impl;

import com.example.demo.entity.DemandReading;
import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.entity.SupplyForecast;
import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemandReadingRepository;
import com.example.demo.repository.LoadSheddingEventRepository;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.LoadSheddingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class LoadSheddingServiceImpl implements LoadSheddingService {

    private final SupplyForecastRepository forecastRepository;
    private final ZoneRepository zoneRepository;
    private final DemandReadingRepository readingRepository;
    private final LoadSheddingEventRepository eventRepository;

    public LoadSheddingServiceImpl(
            SupplyForecastRepository forecastRepository,
            ZoneRepository zoneRepository,
            DemandReadingRepository readingRepository,
            LoadSheddingEventRepository eventRepository
    ) {
        this.forecastRepository = forecastRepository;
        this.zoneRepository = zoneRepository;
        this.readingRepository = readingRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public LoadSheddingEvent triggerLoadShedding(Long forecastId) {

        SupplyForecast forecast = forecastRepository.findById(forecastId)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        List<Zone> activeZones = zoneRepository.findByActiveTrueOrderByPriorityLevelAsc();
        if (activeZones.isEmpty()) {
            throw new BadRequestException("No suitable zones");
        }

        double totalDemand = 0;

        for (Zone zone : activeZones) {
            DemandReading latest = readingRepository
                    .findFirstByZoneIdOrderByRecordedAtDesc(zone.getId())
                    .orElse(null);

            if (latest != null) {
                totalDemand += latest.getDemandMW();
            }
        }

        if (totalDemand <= forecast.getAvailableSupplyMW()) {
            throw new BadRequestException("No overload");
        }

        Zone targetZone = activeZones.get(activeZones.size() - 1);

        DemandReading latestReading = readingRepository
                .findFirstByZoneIdOrderByRecordedAtDesc(targetZone.getId())
                .orElseThrow(() -> new BadRequestException("No suitable zones"));

        double reduction = totalDemand - forecast.getAvailableSupplyMW();

        LoadSheddingEvent event = LoadSheddingEvent.builder()
                .zone(targetZone)
                .eventStart(Instant.now())
                .reason("Automatic load shedding due to overload")
                .triggeredByForecastId(forecastId)
                .expectedDemandReductionMW(Math.max(reduction, 0))
                .build();

        return eventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public LoadSheddingEvent getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoadSheddingEvent> getEventsForZone(Long zoneId) {
        return eventRepository.findByZoneIdOrderByEventStartDesc(zoneId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoadSheddingEvent> getAllEvents() {
        return eventRepository.findAll();
    }
}
