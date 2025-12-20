package com.example.demo.controller;

import com.example.demo.entity.Zone;
import com.example.demo.service.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public Zone createZone(@RequestBody Zone zone) {
        return zoneService.createZone(zone);
    }

    @GetMapping("/{id}")
    public Zone getZone(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @GetMapping
    public List<Zone> getAllZones() {
        return zoneService.getAllZones();
    }
}
