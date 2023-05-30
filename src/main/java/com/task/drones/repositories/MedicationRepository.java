package com.task.drones.repositories;

import com.task.drones.models.Drone;
import com.task.drones.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {
    Medication findByCode(String medicationCode);
    List<Medication> findAllByDrone(Drone drone);
}
