package com.example.demo.controller;



import com.example.demo.entity.DemandReading;

import com.example.demo.service.DemandService;

import org.springframework.web.bind.annotation.*;



@RestController

@RequestMapping("/demand")

public class DemandController {



    private final DemandService demandService;



    public DemandController(DemandService demandService) {

        this.demandService = demandService;

        }



        @PostMapping

        public DemandReading addDemand(@RequestBody DemandReading reading) {

            return demandService.addDemandReading(reading);

            }



            @GetMapping("/latest/{zoneId}")

            public DemandReading getLatestDemand(@PathVariable Long zoneId) {

                return demandService.getLatestDemandForZone(zoneId);

                }

                }