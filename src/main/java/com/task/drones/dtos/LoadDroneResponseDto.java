package com.task.drones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneResponseDto {
    private String serialNumber;
    private String medicationCode;
}
