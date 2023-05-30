package com.task.drones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationResponseDto {
    private String code;
    private String name;
    private Double weight;
    private String image;
}
