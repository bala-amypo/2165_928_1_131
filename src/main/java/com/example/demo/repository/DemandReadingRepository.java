package com.example.demo.repository;

import com.example.demo.entity.DemandReading;
import java.util.*;

public interface DemandReadingRepository {
    Optional<DemandReading> findFirstByZoneIdOrderByRecordedAtDesc(Long zoneId);
    List<DemandReading> findByZoneIdOrderByRecordedAtDesc(Long zoneId);
    DemandReading save(DemandReading reading);
}
