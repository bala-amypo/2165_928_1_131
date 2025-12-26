package com.example.demo.service.impl;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.service.SupplyForecastService;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository repo;

    public SupplyForecastServiceImpl(SupplyForecastRepository repo) {
        this.repo = repo;
    }

    @Override
    public SupplyForecast createForecast(SupplyForecast f) {
        if (f.getAvailableSupplyMW() < 0)
            throw new BadRequestException(">= 0");

        if (!f.getForecastEnd().isAfter(f.getForecastStart()))
            throw new BadRequestException("range");

        f.setGeneratedAt(Instant.now());
        return repo.save(f);
    }

    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast f) {
        SupplyForecast ex = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        if (f.getAvailableSupplyMW() < 0)
            throw new BadRequestException(">= 0");

        if (!f.getForecastEnd().isAfter(f.getForecastStart()))
            throw new BadRequestException("range");

        ex.setAvailableSupplyMW(f.getAvailableSupplyMW());
        ex.setForecastStart(f.getForecastStart());
        ex.setForecastEnd(f.getForecastEnd());

        return repo.save(ex);
    }

    @Override
    public SupplyForecast getLatestForecast() {
        return repo.findFirstByOrderByGeneratedAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No forecasts"));
    }

    @Override
    public SupplyForecast getForecastById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));
    }

    @Override
    public List<SupplyForecast> getAllForecasts() {
        return repo.findAll();
    }
}
