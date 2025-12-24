package com.example.demo.service;

import com.example.demo.entity.SupplyForecast;
import java.util.*;

public interface SupplyForecastService {
    SupplyForecast createForecast(SupplyForecast forecast);
    SupplyForecast updateForecast(Long id, SupplyForecast forecast);
    SupplyForecast getLatestForecast();
    List<SupplyForecast> getAllForecasts();
}
