package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.ZoneRestorationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneRestorationServiceImpl implements ZoneRestorationService {

    private final ZoneRestorationRecordRepository recordRepo;
    private final LoadSheddingEventRepository eventRepo;
    private final ZoneRepository zoneRepo;

    public ZoneRestorationServiceImpl(ZoneRestorationRecordRepository r,
                                      LoadSheddingEventRepository e,
                                      ZoneRepository z) {
        this.recordRepo = r;
        this.eventRepo = e;
        this.zoneRepo = z;
    }

    @Override
    public ZoneRestorationRecord restoreZone(ZoneRestorationRecord r) {

        LoadSheddingEvent ev = eventRepo.findById(r.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Zone z = zoneRepo.findById(r.getZone().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        if (!r.getRestoredAt().isAfter(ev.getEventStart())) {
            throw new BadRequestException("after event start");
        }

        r.setZone(z);
        return recordRepo.save(r);
    }

    @Override
    public ZoneRestorationRecord getRecordById(Long id) {
        return recordRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    @Override
    public List<ZoneRestorationRecord> getRecordsForZone(Long zoneId) {
        return recordRepo.findByZoneIdOrderByRestoredAtDesc(zoneId);
    }
}
