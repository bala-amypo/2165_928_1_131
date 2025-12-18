package com.example.demo.service;



import com.example.demo.entity.SupplyForecast;

import com.example.demo.repository.SupplyForecastRepository;

import org.springframework.stereotype.Service;



@Service

public class ForecastServiceImpl implements ForecastService {



    private final SupplyForecastRepository forecastRepo;



    public ForecastServiceImpl(SupplyForecastRepository forecastRepo) {

        this.forecastRepo = forecastRepo;

        }



        @Override

        public SupplyForecast generateForecast(Double supplyMW) {



            if (supplyMW < 0) {

            throw new IllegalArgumentException("Supply cannot be negative");

            }



            SupplyForecast forecast = SupplyForecast.builder()

            .availableSupplyMW(supplyMW)

            .build();



            return forecastRepo.save(forecast);

            }

            }

           