package com.example.demo.service;



import com.example.demo.entity.DemandReading;



public interface DemandService {



    DemandReading addDemandReading(DemandReading reading);



    DemandReading getLatestDemandForZone(Long zoneId);

    }


