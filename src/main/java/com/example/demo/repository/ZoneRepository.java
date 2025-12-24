package com.example.demo.repository;

import com.example.demo.entity.Zone;
import java.util.*;

public interface ZoneRepository {
    Optional<Zone> findById(Long id);
    Optional<Zone> findByZoneName(String zoneName);
    List<Zone> findAll();
    List<Zone> findByActiveTrueOrderByPriorityLevelAsc();
    Zone save(Zone zone);
}
