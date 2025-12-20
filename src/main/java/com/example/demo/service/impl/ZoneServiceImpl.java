package com.example.demo.service.impl;

import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    // ⚠️ Constructor order is IMPORTANT
    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone createZone(Zone zone) {

        // 🔴 Validation: priority >= 1
        if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {
            throw new BadRequestException("Priority level must be >= 1");
        }

        // 🔴 Validation: unique zone name
        zoneRepository.findByZoneName(zone.getZoneName())
                .ifPresent(z -> {
                    throw new BadRequestException("Zone name must be unique");
                });

        // Defaults handled in entity (@PrePersist)
        return zoneRepository.save(zone);
    }

    @Override
    public Zone updateZone(Long id, Zone zone) {

        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        existing.setZoneName(zone.getZoneName());
        existing.setPriorityLevel(zone.getPriorityLevel());
        existing.setPopulation(zone.getPopulation());

        return zoneRepository.save(existing);
    }

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @Override
    public void deactivateZone(Long id) {

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        zone.setActive(false);
        zoneRepository.save(zone);
    }
}
