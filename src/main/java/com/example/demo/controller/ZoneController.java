package com.example.demo.controller;

import com.example.demo.entity.Zone;
import com.example.demo.service.ZoneService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService service;

    public ZoneController(ZoneService service) {
        this.service = service;
    }

    @PostMapping
    public Zone create(@RequestBody Zone zone) {
        return service.createZone(zone);
    }

    @GetMapping("/{id}")
    public Zone get(@PathVariable Long id) {
        return service.getZoneById(id);
    }
}
