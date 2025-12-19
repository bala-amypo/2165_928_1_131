package com.example.demo.service;

import com.example.demo.entity.LoadSheddingEvent;
import java.util.List;

public interface LoadSheddingService {

    void triggerLoadShedding(Long forecastId);

    LoadSheddingEvent getEventById(Long id);

    List<LoadSheddingEvent> getEventsForZone(Long zoneId);

    List<LoadSheddingEvent> getAllEvents();
}
