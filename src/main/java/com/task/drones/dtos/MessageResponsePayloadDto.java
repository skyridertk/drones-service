package com.task.drones.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponsePayloadDto {
    private int code;
    private String message;
    private Object payload;
}


