package com.example.demo.repository;

import com.example.demo.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    // You can remove findById, findAll, and save because JpaRepository provides them!

    Optional<Zone> findByZoneName(String zoneName);

    // This custom query method is valid and Spring will implement it for you
    List<Zone> findByActiveTrueOrderByPriorityLevelAsc();
}