package com.example.demo.service;



import com.example.demo.entity.Zone;



import java.util.List;



public interface ZoneService {



Zone createZone(Zone zone);



Zone getZoneByName(String name);



List<Zone> getActiveZonesByPriority();

}

