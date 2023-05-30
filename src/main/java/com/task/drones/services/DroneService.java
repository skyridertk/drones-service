package com.task.drones.services;

import com.task.drones.dtos.*;
import com.task.drones.exception.CustomException;
import com.task.drones.models.Drone;
import com.task.drones.models.Medication;
import com.task.drones.models.Model;
import com.task.drones.models.State;
import com.task.drones.repositories.DroneRepository;
import com.task.drones.repositories.MedicationRepository;
import com.task.drones.repositories.ModelRepository;
import com.task.drones.repositories.StateRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class DroneService {
    public final Logger logger = LoggerFactory.getLogger(DroneService.class);

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private StateRepository stateRepository;


    public Drone addDrone(DroneRequestDto droneRequestDto) {

        Drone found = droneRepository.findBySerialNumber(droneRequestDto.getSerialNumber());
        if (found != null) {
            throw new CustomException("Drone with serial number " + droneRequestDto.getSerialNumber() + " already exists", HttpStatus.BAD_REQUEST);
        }

        Drone drone = new Drone();
        if (droneRequestDto.getSerialNumber().length() > 100) {
            throw new CustomException("Serial number cannot be more than 100 characters long", HttpStatus.BAD_REQUEST);
        }

        Model model = modelRepository.findByName(droneRequestDto.getModel().substring(0, 1).toUpperCase() + droneRequestDto.getModel().substring(1).toLowerCase());
        if (model == null) {
            throw new CustomException("Model not found", HttpStatus.BAD_REQUEST);
        }

        if (droneRequestDto.getWeightLimit() > 500) {
            logger.info("Weight limit cannot be more than 500 grams " + HttpStatus.BAD_REQUEST.value());
            throw new CustomException("Weight limit cannot be more than 500 grams", HttpStatus.BAD_REQUEST);
        }

        State state = stateRepository.findByName(droneRequestDto.getState().toUpperCase());
        if (state == null) {
            throw new CustomException("State not found", HttpStatus.BAD_REQUEST);
        }

        drone.setSerialNumber(droneRequestDto.getSerialNumber());
        drone.setModel(model);
        drone.setBatteryCapacity(droneRequestDto.getBatteryCapacity());
        drone.setWeightLimit(droneRequestDto.getWeightLimit());
        drone.setState(state);

        Drone savedDrone = droneRepository.save(drone);

        logger.info("Drone registration request completed");

        return savedDrone;
    }

    public Medication createMedication(MedicationRequestDto medicationRequestDto){
        Medication medication = new Medication();

        if (!medicationRequestDto.getCode().matches("^[A-Z0-9_]*$")) {
            throw new CustomException("Medication code can only contain upper case letters, numbers and ‘_’", HttpStatus.BAD_REQUEST);
        }

        if (!medicationRequestDto.getName().matches("^[a-zA-Z0-9_-]*$")) {
            throw new CustomException("Medication code can only contain letters, numbers, ‘-‘, ‘_’", HttpStatus.BAD_REQUEST);
        }

        medication.setCode(medicationRequestDto.getCode());
        medication.setName(medicationRequestDto.getName());
        medication.setWeight(medicationRequestDto.getWeight());
        medication.setImage(medicationRequestDto.getImage());

        return medicationRepository.save(medication);
    }

    public Boolean load(LoadDroneRequestDto loadDroneRequestDto) {

        Drone drone = droneRepository.findBySerialNumber(loadDroneRequestDto.getSerialNumber());
        logger.info("Drone found: " + drone);
        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        Medication medication = medicationRepository.findByCode(loadDroneRequestDto.getMedicationCode());
        logger.info("Medication found: " + medication);
        if (medication == null) {
            throw new CustomException("Medication not found", HttpStatus.BAD_REQUEST);
        }

        if (medication.getDrone() != null) {
            throw new CustomException("Medication already loaded", HttpStatus.BAD_REQUEST);
        }

        if (drone.getState().getName().equals("LOADED")) {
            throw new CustomException("Drone is loaded", HttpStatus.BAD_REQUEST);
        }

        if (drone.getBatteryCapacity() >= 25) {
            drone.setState(stateRepository.findByName("LOADING"));
            droneRepository.save(drone);
        }

        if (!drone.getState().getName().equals("LOADING")) {
            throw new CustomException("Drone is not in loading state", HttpStatus.BAD_REQUEST);
        }

        List<Medication> medications = medicationRepository.findAllByDrone(droneRepository.findBySerialNumber(loadDroneRequestDto.getSerialNumber()));
        logger.info("Medications found: " + medications.size());

        double totalWeight = medications.stream().mapToDouble(Medication::getWeight).sum();
        logger.info("Total weight: " + totalWeight);

        if (totalWeight + medication.getWeight() > drone.getWeightLimit()) {
            throw new CustomException("Drone cannot load medication as it exceeds its carrying capacity", HttpStatus.BAD_REQUEST);
        }

        medication.setDrone(drone);
        medicationRepository.save(medication);

        return true;
    }

    public Double batteryLevel(String serial) {
        logger.info("Battery level request received for drone with serial number " + serial);
        Drone drone = droneRepository.findBySerialNumber(serial);

        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        return drone.getBatteryCapacity();
    }

    public List<Drone> availableDrones() {

        State state = stateRepository.findByName("IDLE");
        List<Drone> drones = droneRepository.findAllByState(state);
        if (drones.size() == 0){
            throw new CustomException("No drones available", HttpStatus.BAD_REQUEST);
        }

        return drones;

    }

    public List<Medication> checkDronesPayload(String serial) {

        Drone drone = droneRepository.findBySerialNumber(serial);

        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        return medicationRepository.findAllByDrone(drone);

    }
}
