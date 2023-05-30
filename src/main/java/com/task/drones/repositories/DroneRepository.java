package com.task.drones.repositories;

import com.task.drones.models.Drone;
import com.task.drones.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {
    Drone findBySerialNumber(String serialNumber);
    List<Drone> findAllByState(State state);
}
