package com.example.demo.service;

import com.example.demo.entity.Zone;

public interface ZoneService {
    Zone createZone(Zone zone);
    Zone getZoneById(Long id);
}
