package com.task.drones.repositories;

import com.task.drones.models.Drone;
import com.task.drones.models.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MedicationRepositoryTest {
    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private  DroneRepository droneRepository;

    @Test
    void itShouldFindByCode() {
        String code = "PCTMOL";
        Medication medication = medicationRepository.findByCode(code);
        assertThat(medication.getCode()).isEqualTo(code);
    }

    @Test
    void itShouldFindAllByDrone() {
        List<Medication> medications = medicationRepository.findAllByDrone(droneRepository.findBySerialNumber("DRN0001"));
        assertThat(medications).isNotNull();
    }
}