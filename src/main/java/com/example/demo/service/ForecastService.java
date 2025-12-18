package com.example.demo.service;



import com.example.demo.entity.SupplyForecast;



public interface ForecastService {



    SupplyForecast generateForecast(Double supplyMW);

    }

    
}