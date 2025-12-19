package com.example.demo.service;

import com.example.demo.entity.ZoneRestorationRecord;
import com.example.demo.repository.ZoneRestorationRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneRestorationServiceImpl implements ZoneRestorationService {

    private final ZoneRestorationRecordRepository recordRepo;

    public ZoneRestorationServiceImpl(ZoneRestorationRecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }

    @Override
    public ZoneRestorationRecord restoreZone(ZoneRestorationRecord record) {
        return recordRepo.save(record);
    }

    @Override
    public ZoneRestorationRecord getRecordById(Long id) {
        return recordRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));
    }

    @Override
    public List<ZoneRestorationRecord> getRecordsForZone(Long zoneId) {
        return recordRepo.findByZoneIdOrderByRestoredAtDesc(zoneId);
    }
}
