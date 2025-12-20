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

    // ✅ Constructor injection (tests depend on this)
    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    // ---------------- CREATE ZONE ----------------
    @Override
    public Zone createZone(Zone zone) {

        // 🔴 priority validation
        if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {
            throw new BadRequestException("Priority level must be >= 1");
        }

        // 🔴 unique name validation
        zoneRepository.findByZoneName(zone.getZoneName())
                .ifPresent(z -> {
                    throw new BadRequestException("Zone name must be unique");
                });

        // 🔴 default active = true (extra safety)
        if (zone.getActive() == null) {
            zone.setActive(true);
        }

        return zoneRepository.save(zone);
    }

    // ---------------- UPDATE ZONE ----------------
    @Override
    public Zone updateZone(Long id, Zone zone) {

        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );

        // 🔴 priority validation
        if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {
            throw new BadRequestException("Priority level must be >= 1");
        }

        // 🔴 unique name validation (ignore same record)
        zoneRepository.findByZoneName(zone.getZoneName())
                .ifPresent(z -> {
                    if (!z.getId().equals(id)) {
                        throw new BadRequestException("Zone name must be unique");
                    }
                });

        existing.setZoneName(zone.getZoneName());
        existing.setPriorityLevel(zone.getPriorityLevel());
        existing.setPopulation(zone.getPopulation());

        // updatedAt handled automatically by @PreUpdate
        return zoneRepository.save(existing);
    }

    // ---------------- GET ZONE BY ID ----------------
    @Override
    public Zone getZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );
    }

    // ---------------- GET ALL ZONES ----------------
    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    // ---------------- DEACTIVATE ZONE ----------------
    @Override
    public void deactivateZone(Long id) {

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );

        zone.setActive(false);
        zoneRepository.save(zone);
    }
}
