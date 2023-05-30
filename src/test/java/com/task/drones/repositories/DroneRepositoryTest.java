package com.task.drones.repositories;

import com.task.drones.models.Drone;
import com.task.drones.models.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class DroneRepositoryTest {
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private StateRepository stateRepository;
    @Test
    void itShouldFindBySerialNumber() {
        String serialNumber = "DRN0001";

        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        assertThat(drone.getSerialNumber()).isEqualTo(serialNumber);
    }

    @Test
    void itShouldFindAllByState() {
        State state = stateRepository.findByName("IDLE");
        List<Drone> drone = droneRepository.findAllByState(state);
        assertThat(drone).isNotNull();
    }
}