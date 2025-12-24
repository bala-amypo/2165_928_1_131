package com.example.demo.repository;

import com.example.demo.entity.SupplyForecast;
import java.util.*;

public interface SupplyForecastRepository {
    Optional<SupplyForecast> findById(Long id);
    Optional<SupplyForecast> findFirstByOrderByGeneratedAtDesc();
    List<SupplyForecast> findAll();
    SupplyForecast save(SupplyForecast forecast);
}
