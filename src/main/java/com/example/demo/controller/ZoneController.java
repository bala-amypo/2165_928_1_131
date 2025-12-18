package com.example.demo.controller;



import com.example.demo.entity.Zone;

import com.example.demo.service.ZoneService;

import org.springframework.web.bind.annotation.*;



import java.util.List;



@RestController

@RequestMapping("/zones")

public class ZoneController {



private final ZoneService zoneService;



public ZoneController(ZoneService zoneService) {

    this.zoneService = zoneService;

    }



    @PostMapping

    public Zone createZone(@RequestBody Zone zone) {

    return zoneService.createZone(zone);

    }



    @GetMapping("/{name}")

    public Zone getZoneByName(@PathVariable String name) {

        return zoneService.getZoneByName(name);

        }



        @GetMapping("/active")

        public List<Zone> getActiveZones() {

        return zoneService.getActiveZonesByPriority();

        }

        }


    
