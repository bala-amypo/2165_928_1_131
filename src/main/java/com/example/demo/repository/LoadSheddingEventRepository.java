package com.example.demo.repository;

import com.example.demo.entity.LoadSheddingEvent;
import java.util.*;

public interface LoadSheddingEventRepository {
    Optional<LoadSheddingEvent> findById(Long id);
    List<LoadSheddingEvent> findAll();
    List<LoadSheddingEvent> findByZoneIdOrderByEventStartDesc(Long zoneId);
    LoadSheddingEvent save(LoadSheddingEvent event);
}
