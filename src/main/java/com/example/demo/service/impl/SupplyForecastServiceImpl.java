package com.example.demo.service;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.repository.SupplyForecastRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository forecastRepo;

    public SupplyForecastServiceImpl(SupplyForecastRepository forecastRepo) {
        this.forecastRepo = forecastRepo;
    }

    @Override
    public SupplyForecast createForecast(SupplyForecast forecast) {
        if (forecast.getAvailableSupplyMW() < 0) {
            throw new IllegalArgumentException("Supply must be >= 0");
        }
        return forecastRepo.save(forecast);
    }

    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast forecast) {
        SupplyForecast existing = getForecastById(id);
        existing.setAvailableSupplyMW(forecast.getAvailableSupplyMW());
        existing.setForecastStart(forecast.getForecastStart());
        existing.setForecastEnd(forecast.getForecastEnd());
        return forecastRepo.save(existing);
    }

    @Override
    public SupplyForecast getForecastById(Long id) {
        return forecastRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Forecast not found"));
    }

    @Override
    public SupplyForecast getLatestForecast() {
        return forecastRepo.findFirstByOrderByGeneratedAtDesc()
                .orElseThrow(() -> new IllegalArgumentException("No forecast found"));
    }

    @Override
    public List<SupplyForecast> getAllForecasts() {
        return forecastRepo.findAll();
    }
}
