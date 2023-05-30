package com.task.drones.jobs;

import com.task.drones.models.Drone;
import com.task.drones.repositories.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBatteryDroneScheduler {
    private static final Logger logger = LoggerFactory.getLogger(GetBatteryDroneScheduler.class);
    @Autowired
    private DroneRepository droneRepository;

    @Scheduled(fixedRate = 300000)
    @Async
    public void getBatteryDrone(){
        List<Drone> drones = droneRepository.findAll();

        for (Drone drone : drones) {
            logger.info("Drone: " + drone.getSerialNumber() + " - Battery: " + drone.getBatteryCapacity());
        }

        logger.info("GetBatteryDroneScheduler done");
    }
}
