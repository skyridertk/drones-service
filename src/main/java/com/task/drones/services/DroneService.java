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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService {
    public Logger logger = LoggerFactory.getLogger(DroneService.class);

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MessageResponseDto addDrone(HttpServletRequest request, DroneRequestDto droneRequestDto) {

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

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setMessage("Drone with serial number " + savedDrone.getSerialNumber() + " successfully registered");
        messageResponseDto.setCode(HttpStatus.OK.value());

        return modelMapper.map(messageResponseDto, MessageResponseDto.class);
    }

    public MedicationResponseDto createMedication(HttpServletRequest request, MedicationRequestDto medicationRequestDto) throws Exception {
        try {
            Medication medication = new Medication();

            if (!medicationRequestDto.getCode().matches("^[A-Z0-9_]*$")) {
                throw new Exception("Medication code can only contain upper case letters, numbers and ‘_’");
            }

            if (!medicationRequestDto.getName().matches("^[a-zA-Z0-9_-]*$")) {
                throw new Exception("Medication code can only contain letters, numbers, ‘-‘, ‘_’");
            }

            medication.setCode(medicationRequestDto.getCode());
            medication.setName(medicationRequestDto.getName());
            medication.setWeight(medicationRequestDto.getWeight());
            medication.setImage(medicationRequestDto.getImage());

            Medication savedMedication = medicationRepository.save(medication);

            return modelMapper.map(savedMedication, MedicationResponseDto.class);
        } catch (Exception e) {
            throw new Exception(e.getLocalizedMessage());
        }
    }

    public MessageResponseDto load(HttpServletRequest request, LoadDroneRequestDto loadDroneRequestDto) {

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

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Medication successfully loaded");
        responseDto.setCode(HttpStatus.OK.value());

        return modelMapper.map(responseDto, MessageResponseDto.class);
    }

    public MessageResponsePayloadDto batteryLevel(HttpServletRequest request, String serial) {
        logger.info("Battery level request received for drone with serial number " + serial);
        Drone drone = droneRepository.findBySerialNumber(serial);

        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Battery level for drone with serial number " + serial);
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(drone.getBatteryCapacity());

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);
    }

    public MessageResponsePayloadDto availableDrones(HttpServletRequest request) {

        State state = stateRepository.findByName("IDLE");
        List<Drone> drone = droneRepository.findAllByState(state);
        if (drone.size() == 0){
            throw new CustomException("No drones available", HttpStatus.BAD_REQUEST);
        }

        List<DroneResponseDto> listOfDrones = drone
                .stream()
                .filter(drone1 -> drone1.getBatteryCapacity() >= 25)
                .map(drone1 -> modelMapper.map(drone1, DroneResponseDto.class)).toList();

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Success");
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(listOfDrones);
        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);

    }

    public MessageResponsePayloadDto checkLoadedMedicationDrone(HttpServletRequest request, String serial) {

        Drone drone = droneRepository.findBySerialNumber(serial);

        if (drone == null) {
            throw new CustomException("Drone not found", HttpStatus.BAD_REQUEST);
        }

        List<Medication> medications = medicationRepository.findAllByDrone(drone);
        List<MedicationResponseDto> listOfMedications = medications
                .stream()
                .map(medication -> modelMapper.map(medication, MedicationResponseDto.class)).toList();

        MessageResponsePayloadDto responseDto = new MessageResponsePayloadDto();
        responseDto.setMessage("Success");
        responseDto.setCode(HttpStatus.OK.value());
        responseDto.setPayload(listOfMedications);

        return modelMapper.map(responseDto, MessageResponsePayloadDto.class);

    }
}
