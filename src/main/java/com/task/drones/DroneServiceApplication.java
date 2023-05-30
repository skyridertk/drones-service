package com.task.drones;

import com.task.drones.models.Drone;
import com.task.drones.models.Medication;
import com.task.drones.models.Model;
import com.task.drones.models.State;
import com.task.drones.repositories.DroneRepository;
import com.task.drones.repositories.MedicationRepository;
import com.task.drones.repositories.ModelRepository;
import com.task.drones.repositories.StateRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DroneServiceApplication implements CommandLineRunner {

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    MedicationRepository medicationRepository;

    Logger logger = LoggerFactory.getLogger(DroneServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DroneServiceApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Override
    public void run(String... params) throws Exception {
        long numberOfModels = modelRepository.count();
        long numberOfStates = stateRepository.count();
        long numberOfMedications = medicationRepository.count();
        long numberOfDrones = droneRepository.count();

        if (numberOfModels == 0) {
            Model lightweight = new Model();
            lightweight.setName("Lightweight");
            modelRepository.save(lightweight);

            logger.info("Lightweight model created");

            Model middleweight = new Model();
            middleweight.setName("Middleweight");
            modelRepository.save(middleweight);

            logger.info("Middleweight model created");

            Model cruiserweight = new Model();
            cruiserweight.setName("Cruiserweight");
            modelRepository.save(cruiserweight);

            logger.info("Cruiserweight model created");

            Model heavyweight = new Model();
            heavyweight.setName("Heavyweight");
            modelRepository.save(heavyweight);

            logger.info("Heavyweight model created");
        }

        if (numberOfStates == 0){
            State idle = new State();
            idle.setName("IDLE");
            stateRepository.save(idle);

            logger.info("IDLE state created");

            State loading = new State();
            loading.setName("LOADING");
            stateRepository.save(loading);

            logger.info("LOADING state created");

            State loaded = new State();
            loaded.setName("LOADED");
            stateRepository.save(loaded);

            logger.info("LOADED state created");

            State delivering = new State();
            delivering.setName("DELIVERING");
            stateRepository.save(delivering);

            logger.info("DELIVERING state created");

            State delivered = new State();
            delivered.setName("DELIVERED");
            stateRepository.save(delivered);

            logger.info("DELIVERED state created");

            State returning = new State();
            returning.setName("RETURNING");
            stateRepository.save(returning);

            logger.info("RETURNING state created");
        }

        if (numberOfMedications == 0){
            Medication med1 = new Medication();
            med1.setName("Paracetamol");
            med1.setWeight(200.0);
            med1.setCode("PCTMOL");
            med1.setImage("https://www.drugs.com/images/pills/generic/paracetamol-325-mg-1.jpg");
            medicationRepository.save(med1);

            logger.info("Paracetamol medication created");

            Medication med2 = new Medication();
            med2.setName("Ibuprofen");
            med2.setWeight(150.0);
            med2.setCode("IBUPROF");
            med2.setImage("https://www.drugs.com/images/pills/generic/ibuprofen-200-mg-1.jpg");
            medicationRepository.save(med2);

            logger.info("Ibuprofen medication created");

            Medication med3 = new Medication();
            med3.setName("Aspirin");
            med3.setWeight(100.0);
            med3.setCode("ASPRIN");
            med3.setImage("https://www.drugs.com/images/pills/generic/aspirin-325-mg-1.jpg");
            medicationRepository.save(med3);

            logger.info("Aspirin medication created");

            Medication med4 = new Medication();
            med4.setName("Caffeine");
            med4.setWeight(350.0);
            med4.setCode("CAFFINE");
            med4.setImage("https://www.drugs.com/images/pills/generic/caffeine-200-mg-1.jpg");
            medicationRepository.save(med4);

            logger.info("Caffeine medication created");

            Medication med5 = new Medication();
            med5.setName("Codeine");
            med5.setWeight(250.0);
            med5.setCode("CODEINE");
            med5.setImage("https://www.drugs.com/images/pills/generic/codeine-30-mg-1.jpg");
            medicationRepository.save(med5);

            logger.info("Codeine medication created");

            Medication med6 = new Medication();
            med6.setName("Dextromethorphan");
            med6.setWeight(300.0);
            med6.setCode("DEXMETH");
            med6.setImage("https://www.drugs.com/images/pills/generic/dextromethorphan-30-mg-1.jpg");
            medicationRepository.save(med6);

            logger.info("Dextromethorphan medication created");

            Medication med7 = new Medication();
            med7.setName("Diphenhydramine");
            med7.setWeight(50.0);
            med7.setCode("DIPHENH");
            med7.setImage("https://www.drugs.com/images/pills/generic/diphenhydramine-25-mg-1.jpg");
            medicationRepository.save(med7);

            logger.info("Diphenhydramine medication created");

            Medication med8 = new Medication();
            med8.setName("Doxylamine");
            med8.setWeight(80.0);
            med8.setCode("DOXYLAM");
            med8.setImage("https://www.drugs.com/images/pills/generic/doxylamine-25-mg-1.jpg");
            medicationRepository.save(med8);

            logger.info("Doxylamine medication created");

            Medication med9 = new Medication();
            med9.setName("Hydrocodone");
            med9.setWeight(10.0);
            med9.setCode("HYDROCO");
            med9.setImage("https://www.drugs.com/images/pills/generic/hydrocodone-5-mg-1.jpg");
            medicationRepository.save(med9);

            logger.info("Hydrocodone medication created");

            Medication med10 = new Medication();
            med10.setName("Hydromorphone");
            med10.setWeight(20.0);
            med10.setCode("HYDROMO");
            med10.setImage("https://www.drugs.com/images/pills/generic/hydromorphone-2-mg-1.jpg");
            medicationRepository.save(med10);

            logger.info("Hydromorphone medication created");



        }

        if (numberOfDrones < 10){
            Drone drone1 = new Drone();
            drone1.setSerialNumber("DRN0001");
            drone1.setBatteryCapacity(15.0);
            drone1.setWeightLimit(200);
            drone1.setModel(modelRepository.findByName("Lightweight"));
            drone1.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone1);

            logger.info("Drone 1 created");

            Drone drone2 = new Drone();
            drone2.setSerialNumber("DRN0002");
            drone2.setBatteryCapacity(75.0);
            drone2.setWeightLimit(250);
            drone2.setModel(modelRepository.findByName("Middleweight"));
            drone2.setState(stateRepository.findByName("RETURNING"));
            droneRepository.save(drone2);

            logger.info("Drone 2 created");

            Drone drone3 = new Drone();
            drone3.setSerialNumber("DRN0003");
            drone3.setBatteryCapacity(100.0);
            drone3.setWeightLimit(300);
            drone3.setModel(modelRepository.findByName("Heavyweight"));
            drone3.setState(stateRepository.findByName("DELIVERING"));
            droneRepository.save(drone3);

            logger.info("Drone 3 created");

            Drone drone4 = new Drone();
            drone4.setSerialNumber("DRN0004");
            drone4.setBatteryCapacity(100.0);
            drone4.setWeightLimit(300);
            drone4.setModel(modelRepository.findByName("Cruiserweight"));
            drone4.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone4);

            logger.info("Drone 4 created");

            Drone drone5 = new Drone();
            drone5.setSerialNumber("DRN0005");
            drone5.setBatteryCapacity(40.0);
            drone5.setWeightLimit(450);
            drone5.setModel(modelRepository.findByName("Heavyweight"));
            drone5.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone5);

            logger.info("Drone 5 created");

            Drone drone6 = new Drone();
            drone6.setSerialNumber("DRN0006");
            drone6.setBatteryCapacity(100.0);
            drone6.setWeightLimit(300);
            drone6.setModel(modelRepository.findByName("Heavyweight"));
            drone6.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone6);

            logger.info("Drone 6 created");

            Drone drone7 = new Drone();
            drone7.setSerialNumber("DRN0007");
            drone7.setBatteryCapacity(100.0);
            drone7.setWeightLimit(300);
            drone7.setModel(modelRepository.findByName("Heavyweight"));
            drone7.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone7);

            logger.info("Drone 7 created");

            Drone drone8 = new Drone();
            drone8.setSerialNumber("DRN0008");
            drone8.setBatteryCapacity(100.0);
            drone8.setWeightLimit(300);
            drone8.setModel(modelRepository.findByName("Heavyweight"));
            drone8.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone8);

            logger.info("Drone 8 created");

            Drone drone9 = new Drone();
            drone9.setSerialNumber("DRN0009");
            drone9.setBatteryCapacity(100.0);
            drone9.setWeightLimit(300);
            drone9.setModel(modelRepository.findByName("Heavyweight"));
            drone9.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone9);

            logger.info("Drone 9 created");

            Drone drone10 = new Drone();
            drone10.setSerialNumber("DRN0010");
            drone10.setBatteryCapacity(10.0);
            drone10.setWeightLimit(400);
            drone10.setModel(modelRepository.findByName("Heavyweight"));
            drone10.setState(stateRepository.findByName("IDLE"));
            droneRepository.save(drone10);

            logger.info("Drone 10 created");


        }

        logger.info("Running DroneServiceApplication...");
    }

}
