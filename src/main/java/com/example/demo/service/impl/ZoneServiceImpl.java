package com.example.demo.service.impl;

import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service   // ✅ REQUIRED
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepo;

    public ZoneServiceImpl(ZoneRepository zoneRepo) {
        this.zoneRepo = zoneRepo;
    }

    @Override
    public Zone createZone(Zone z) {
        if (z.getPriorityLevel() == null || z.getPriorityLevel() < 1)
            throw new BadRequestException(">= 1");

        if (zoneRepo.findByZoneName(z.getZoneName()).isPresent())
            throw new BadRequestException("unique");

        z.setActive(z.getActive() != null ? z.getActive() : true);
        z.setUpdatedAt(Instant.now());

        return zoneRepo.save(z);
    }

    @Override
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

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepo.findAll();
    }

    @Override
    public void deactivateZone(Long id) {
        Zone z = getZoneById(id);
        z.setActive(false);
        z.setUpdatedAt(Instant.now());
        zoneRepo.save(z);
    }
}
