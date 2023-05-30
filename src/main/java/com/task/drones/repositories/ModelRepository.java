package com.task.drones.repositories;

import com.task.drones.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByName(String name);
}
