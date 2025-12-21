package com.example.demo.controller;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.service.SupplyForecastService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-forecasts")
public class SupplyForecastController {

    private final SupplyForecastService supplyForecastService;

    public SupplyForecastController(SupplyForecastService supplyForecastService) {
        this.supplyForecastService = supplyForecastService;
    }

    @PostMapping
    public SupplyForecast create(@RequestBody SupplyForecast forecast) {
        return supplyForecastService.createForecast(forecast);
    }

    @PutMapping("/{id}")
    public SupplyForecast update(@PathVariable Long id,
                                 @RequestBody SupplyForecast forecast) {
        return supplyForecastService.updateForecast(id, forecast);
    }

    @GetMapping("/{id}")
    public SupplyForecast getById(@PathVariable Long id) {
        return supplyForecastService.getForecastById(id);
    }

    @GetMapping("/latest")
    public SupplyForecast getLatest() {
        return supplyForecastService.getLatestForecast();
    }

    @GetMapping
    public List<SupplyForecast> getAll() {
        return supplyForecastService.getAllForecasts();
    }
}
