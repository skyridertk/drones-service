package com.task.drones.dtos;

import com.task.drones.models.Model;
import com.task.drones.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationRequestDto {
    private String code;
    private String name;
    private Double weight;
    private String Image;
}
