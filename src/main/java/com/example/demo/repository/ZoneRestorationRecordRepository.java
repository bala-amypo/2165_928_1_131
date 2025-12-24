package com.example.demo.repository;

import com.example.demo.entity.ZoneRestorationRecord;
import java.util.*;

public interface ZoneRestorationRecordRepository {
    Optional<ZoneRestorationRecord> findById(Long id);
    List<ZoneRestorationRecord> findByZoneIdOrderByRestoredAtDesc(Long zoneId);
    ZoneRestorationRecord save(ZoneRestorationRecord record);
}
