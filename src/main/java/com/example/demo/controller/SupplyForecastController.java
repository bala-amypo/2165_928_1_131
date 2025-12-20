package com.example.demo.controller;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.service.SupplyForecastService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-forecasts")
public class SupplyForecastController {

    private final SupplyForecastService forecastService;

    public SupplyForecastController(SupplyForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping
    public SupplyForecast createForecast(@RequestBody SupplyForecast forecast) {
        return forecastService.createForecast(forecast);
    }

    @PutMapping("/{id}")
    public SupplyForecast updateForecast(
            @PathVariable Long id,
            @RequestBody SupplyForecast forecast) {
        return forecastService.updateForecast(id, forecast);
    }

    @GetMapping("/{id}")
    public SupplyForecast getForecastById(@PathVariable Long id) {
        return forecastService.getForecastById(id);
    }

    @GetMapping("/latest")
    public SupplyForecast getLatestForecast() {
        return forecastService.getLatestForecast();
    }

    @GetMapping
    public List<SupplyForecast> getAllForecasts() {
        return forecastService.getAllForecasts();
    }
}
