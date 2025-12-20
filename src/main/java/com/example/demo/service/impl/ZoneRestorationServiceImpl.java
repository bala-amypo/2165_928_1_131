package com.example.demo.service.impl;

import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.entity.Zone;
import com.example.demo.entity.ZoneRestorationRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoadSheddingEventRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.repository.ZoneRestorationRecordRepository;
import com.example.demo.service.ZoneRestorationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneRestorationServiceImpl implements ZoneRestorationService {

    private final ZoneRestorationRecordRepository recordRepo;
    private final LoadSheddingEventRepository eventRepo;
    private final ZoneRepository zoneRepo;

    // ✅ EXACT constructor order required by tests
    public ZoneRestorationServiceImpl(
            ZoneRestorationRecordRepository recordRepo,
            LoadSheddingEventRepository eventRepo,
            ZoneRepository zoneRepo) {

        this.recordRepo = recordRepo;
        this.eventRepo = eventRepo;
        this.zoneRepo = zoneRepo;
    }

    // ---------------- RESTORE ZONE ----------------
    @Override
    public ZoneRestorationRecord restoreZone(ZoneRestorationRecord record) {

        // 🔴 Event must exist
        LoadSheddingEvent event = eventRepo.findById(record.getEventId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found")
                );

        // 🔴 Zone must exist
        Zone zone = zoneRepo.findById(record.getZone().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zone not found")
                );

        // 🔴 restoredAt must be AFTER eventStart
        if (!record.getRestoredAt().isAfter(event.getEventStart())) {
            throw new BadRequestException("Restored time must be after event start");
        }

        record.setZone(zone);
        return recordRepo.save(record);
    }

    // ---------------- GET RECORD BY ID ----------------
    @Override
    public ZoneRestorationRecord getRecordById(Long id) {
        return recordRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found")
                );
    }

    // ---------------- GET RECORDS FOR ZONE ----------------
    @Override
    public List<ZoneRestorationRecord> getRecordsForZone(Long zoneId) {
        return recordRepo.findByZoneIdOrderByRestoredAtDesc(zoneId);
    }
}
