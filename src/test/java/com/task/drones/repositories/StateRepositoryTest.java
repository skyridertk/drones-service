package com.task.drones.repositories;

import com.task.drones.models.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StateRepositoryTest {
    @Autowired
    private StateRepository stateRepository;

    @Test
    void itShouldFindStateByName() {
        String state = "IDLE";

        State nameState = stateRepository.findByName(state);
        assertThat(nameState.getName()).isEqualTo(state);
    }

    @Test
    void itShouldNotFindNoneStateByName() {
        String noneExistingState = "IDLES";

        State nameState = stateRepository.findByName(noneExistingState);
        assertThat(nameState).isNull();
    }
}