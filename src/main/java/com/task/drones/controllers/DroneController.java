package com.task.drones.controllers;

import com.task.drones.dtos.*;
import com.task.drones.services.DroneService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/drones")
public class DroneController {
    private static Logger logger = LoggerFactory.getLogger(DroneController.class);
   /*//dsd */
    @Autowired
    private DroneService droneService;

    @PostMapping("/register")
    public MessageResponseDto registerDrone(HttpServletRequest request, @RequestBody DroneRequestDto droneRequestDto) {
        logger.info("Drone registration request received");
        return droneService.addDrone(request, droneRequestDto);
    }

    @PostMapping("/load")
    public MessageResponseDto loadDrone(HttpServletRequest request, @RequestBody LoadDroneRequestDto loadDroneRequestDto) {
        return droneService.load(request, loadDroneRequestDto);
    }

    @GetMapping("/check-load")
    public MessageResponsePayloadDto checkLoadDrones(HttpServletRequest request, @RequestParam("serial") Optional<String> serial) {
        return droneService.checkLoadedMedicationDrone(request, serial.orElse(null));
    }

    @GetMapping("/check-available")
    public MessageResponsePayloadDto checkAvailableDrones(HttpServletRequest request) {
        return droneService.availableDrones(request);
    }

    @GetMapping("/battery")
    public MessageResponsePayloadDto checkBatteryDrones(HttpServletRequest request, @RequestParam("serial") Optional<String> serial) {
        return droneService.batteryLevel(request, serial.orElse(null));
    }
}
