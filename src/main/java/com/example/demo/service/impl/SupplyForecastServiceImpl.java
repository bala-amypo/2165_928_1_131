package com.example.demo.service.impl;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.service.SupplyForecastService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository forecastRepo;

    // ✅ Constructor injection (tests rely on this)
    public SupplyForecastServiceImpl(SupplyForecastRepository forecastRepo) {
        this.forecastRepo = forecastRepo;
    }

    // ---------------- CREATE FORECAST ----------------
    @Override
    public SupplyForecast createForecast(SupplyForecast forecast) {

        // 🔴 Supply must be >= 0
        if (forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("Supply must be >= 0");
        }

        // 🔴 Forecast range validation
        if (!forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("Invalid range");
        }

        return forecastRepo.save(forecast);
    }

    // ---------------- UPDATE FORECAST ----------------
    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast forecast) {

        SupplyForecast existing = forecastRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Forecast not found")
                );

        if (forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("Supply must be >= 0");
        }

        if (!forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("Invalid range");
        }

        existing.setAvailableSupplyMW(forecast.getAvailableSupplyMW());
        existing.setForecastStart(forecast.getForecastStart());
        existing.setForecastEnd(forecast.getForecastEnd());

        return forecastRepo.save(existing);
    }

    // ---------------- GET FORECAST BY ID ----------------
    @Override
    public SupplyForecast getForecastById(Long id) {
        return forecastRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Forecast not found")
                );
    }

    // ---------------- GET LATEST FORECAST ---
