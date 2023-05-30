package com.task.drones.repositories;

import com.task.drones.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);
}
