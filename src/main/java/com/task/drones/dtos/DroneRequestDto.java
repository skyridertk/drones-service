package com.task.drones.dtos;

import com.task.drones.models.Model;
import com.task.drones.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneRequestDto {
    private String serialNumber;
    private String model;
    private int weightLimit;
    private Double batteryCapacity;
    private String state;
}
