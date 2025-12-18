package com.example.demo.service;



import com.example.demo.entity.Zone;

import com.example.demo.repository.ZoneRepository;

import org.springframework.stereotype.Service;



import java.util.List;



@Service

public class ZoneServiceImpl implements ZoneService {



    private final ZoneRepository zoneRepository;



    public ZoneServiceImpl(ZoneRepository zoneRepository) {

        this.zoneRepository = zoneRepository;

        }



        @Override

        public Zone createZone(Zone zone) {



            if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {

                throw new IllegalArgumentException("Priority level must be >= 1");

                }



                zoneRepository.findByZoneName(zone.getZoneName())

                .ifPresent(z -> {

                throw new IllegalArgumentException("Zone name already exists");

                });



                return zoneRepository.save(zone);

                }



                @Override

                public Zone getZoneByName(String name) {

                return zoneRepository.findByZoneName(name)

                .orElseThrow(() -> new IllegalArgumentException("Zone not found"));

                }



                @Override

                public List<Zone> getActiveZonesByPriority() {

                    return zoneRepository.findByActiveTrueOrderByPriorityLevelAsc();

                    }

                    }

    