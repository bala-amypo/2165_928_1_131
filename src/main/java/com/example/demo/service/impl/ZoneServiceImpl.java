package com.example.demo.service.impl;

import com.example.demo.entity.Zone;
import com.example.demo.exception.*;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;

import java.time.Instant;
import java.util.*;

public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepo;

    public ZoneServiceImpl(ZoneRepository zoneRepo) {
        this.zoneRepo = zoneRepo;
    }

    public Zone createZone(Zone z) {
        if (z.getPriorityLevel() == null || z.getPriorityLevel() < 1)
            throw new BadRequestException(">= 1");

        if (zoneRepo.findByZoneName(z.getZoneName()).isPresent())
            throw new BadRequestException("unique");

        z.setActive(z.getActive() != null ? z.getActive() : true);
        return zoneRepo.save(z);
    }

    public Zone updateZone(Long id, Zone z) {
        Zone existing = zoneRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        existing.setZoneName(z.getZoneName());
        existing.setPriorityLevel(z.getPriorityLevel());
        existing.setPopulation(z.getPopulation());
        existing.setActive(z.getActive());
        existing.setUpdatedAt(Instant.now());

        return zoneRepo.save(existing);
    }

    public Zone getZoneById(Long id) {
        return zoneRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    public List<Zone> getAllZones() {
        return zoneRepo.findAll();
    }

    public void deactivateZone(Long id) {
        Zone z = getZoneById(id);
        z.setActive(false);
        zoneRepo.save(z);
    }
}
