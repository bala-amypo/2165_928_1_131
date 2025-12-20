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

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone createZone(Zone zone) {

        if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {
            throw new BadRequestException("priority must be >= 1");
        }

        zoneRepository.findByZoneName(zone.getZoneName())
                .ifPresent(z -> {
                    throw new BadRequestException("zone name must be unique");
                });

        return zoneRepository.save(zone);
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
        Zone zone = getZoneById(id);
        zone.setActive(false);
        zoneRepository.save(zone);
    }
}
