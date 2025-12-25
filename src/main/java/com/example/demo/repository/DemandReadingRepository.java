package com.example.demo.repository;

import com.example.demo.entity.DemandReading;
import java.util.*;

public interface DemandReadingRepository {
    Optional<DemandReading> findFirstByZoneIdOrderByRecordedAtDesc(Long zoneId);
    List<DemandReading> findByZoneIdOrderByRecordedAtDesc(Long zoneId);
    DemandReading save(DemandReading reading);
}package com.example.demo.repository;

import com.example.demo.entity.DemandReading;
import org.springframework.data.jpa.repository.JpaRepository; // Required!
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandReadingRepository extends JpaRepository<DemandReading, Long> {
    
    // Spring Data JPA generates the SQL for these automatically
    Optional<DemandReading> findFirstByZoneIdOrderByRecordedAtDesc(Long zoneId);
    
    List<DemandReading> findByZoneIdOrderByRecordedAtDesc(Long zoneId);
}
