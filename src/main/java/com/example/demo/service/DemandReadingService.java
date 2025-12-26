package com.example.demo.service;

import com.example.demo.entity.DemandReading;
import java.util.List;

public interface DemandReadingService {

    DemandReading save(DemandReading reading);

    List<DemandReading> findAll();
}
