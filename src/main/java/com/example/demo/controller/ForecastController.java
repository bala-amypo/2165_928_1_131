package com.example.demo.controller;



import com.example.demo.entity.SupplyForecast;

import com.example.demo.service.ForecastService;

import org.springframework.web.bind.annotation.*;



@RestController

@RequestMapping("/forecast")

public class ForecastController {



private final ForecastService forecastService;



public ForecastController(ForecastService forecastService) {

    this.forecastService = forecastService;

    }



    @PostMapping

    public SupplyForecast generateForecast(@RequestParam Double supplyMW) {

        return forecastService.generateForecast(supplyMW);

        }

        }

        