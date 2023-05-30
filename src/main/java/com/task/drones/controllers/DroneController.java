package com.task.drones.controllers;

import com.task.drones.dtos.*;
import com.task.drones.models.Drone;
import com.task.drones.models.Medication;
import com.task.drones.services.DroneService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/drones")
public class DroneController {
    private static final Logger logger = LoggerFactory.getLogger(DroneController.class);

    @Autowired
    private DroneService droneService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public MessageResponseDto registerDrone(HttpServletRequest request, @RequestBody DroneRequestDto droneRequestDto) {
        logger.info("Drone registration request received");
        Drone savedDrone =  droneService.addDrone(droneRequestDto);

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setMessage("Drone with serial number " + savedDrone.getSerialNumber() + " successfully registered");
        messageResponseDto.setCode(HttpStatus.OK.value());

        return modelMapper.map(messageResponseDto, MessageResponseDto.class);
    }

    @PostMapping("/medication")
    public MedicationResponseDto createMedication(HttpServletRequest request, @RequestBody MedicationRequestDto medicationRequestDto) {
        logger.info("Drone registration request received");
        Medication savedMedication =  droneService.createMedication(medicationRequestDto);

        return modelMapper.map(savedMedication, MedicationResponseDto.class);
    }

    @PostMapping("/load")
    public MessageResponseDto loadDrone(HttpServletRequest request, @RequestBody LoadDroneRequestDto loadDroneRequestDto) {
        Boolean status =  droneService.load(loadDroneRequestDto);

        MessageResponseDto responseDto = new MessageResponseDto();
        String message = "";
        if(status){
            message = "Medication successfully loaded";
        } else {
            message = "Medication failed to load";
        }
        responseDto.setMessage(message);
        responseDto.setCode(HttpStatus.OK.value());

        return modelMapper.map(responseDto, MessageResponseDto.class);
    }

    @GetMapping("/check-load")
    public MessageResponsePayloadDto dronesPayload(HttpServletRequest request, @RequestParam("serial") Optional<String> serial) {
        List<Medication> medications =  droneService.checkDronesPayload(serial.orElse(null));

        List<MedicationResponseDto> listOfMedications = medications
                .stream()
                .map(medication -> modelMapper.map(medication, MedicationResponseDto.class)).toList();

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Success");
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(listOfMedications);

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);
    }

    @GetMapping("/check-available")
    public MessageResponsePayloadDto checkAvailableDrones(HttpServletRequest request) {
        List<Drone> drones =  droneService.availableDrones();

        List<DroneResponseDto> listOfDrones = drones
                .stream()
                .filter(drone1 -> drone1.getBatteryCapacity() >= 25)
                .map(drone1 -> modelMapper.map(drone1, DroneResponseDto.class)).toList();

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Success");
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(listOfDrones);

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);
    }

    @GetMapping("/battery")
    public MessageResponsePayloadDto checkBatteryDrones(HttpServletRequest request, @RequestParam("serial") Optional<String> serial) {
        Double batteryLevel = droneService.batteryLevel(serial.orElse(null));

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Battery level for drone with serial number " + serial);
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(batteryLevel);

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);
    }
}
