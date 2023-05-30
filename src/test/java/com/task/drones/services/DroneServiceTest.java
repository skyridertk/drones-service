package com.task.drones.services;

import com.task.drones.repositories.DroneRepository;
import com.task.drones.repositories.MedicationRepository;
import com.task.drones.repositories.ModelRepository;
import com.task.drones.repositories.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class DroneServiceTest {

    private DroneService droneService;
    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;
    private ModelRepository modelRepository;
    private StateRepository stateRepository;

    @BeforeEach
    void setUp() {
        droneService = new DroneService(droneRepository, medicationRepository, modelRepository, stateRepository);
    }

    @Test
    @Disabled
    void addDrone() {
    }

    @Test
    @Disabled
    void createMedication() {
    }

    @Test
    @Disabled
    void load() {

    }

    @Test
    @Disabled
    void batteryLevel() {
    }

    @Test
    @Disabled
    void availableDrones() {
    }

    @Test
    @Disabled
    void checkLoadedMedicationDrone() {
    }
}