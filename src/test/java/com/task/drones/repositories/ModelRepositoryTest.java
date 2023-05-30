package com.task.drones.repositories;

import com.task.drones.models.Model;
import com.task.drones.models.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ModelRepositoryTest {
    @Autowired
    private ModelRepository modelRepository;
    @Test
    void itShouldFindByName() {
        String name = "Lightweight";

        Model model = modelRepository.findByName(name);
        assertThat(model.getName()).isEqualTo(name);
    }

    @Test
    void itShouldReturnNullFindByNonExistingName() {
        String name = "Greenweight";

        Model model = modelRepository.findByName(name);
        assertThat(model).isNull();
    }
}