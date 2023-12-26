package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class LicensePlateService {

    @Autowired
    private SimpMessagingTemplate template;



    private final LicensePlateRepository repository;

    @Autowired
    public LicensePlateService(LicensePlateRepository licensePlateRepository) {
        this.repository = licensePlateRepository;
    }

    public List<LicensePlate> getAllLicensePlates() {
        return repository.findAll();
    }

    public void saveLicensePlate(String plateNumber, String driverName) {
        Optional<LicensePlate> latestRecord = repository.findFirstByPlateNumberOrderByTimeInDesc(plateNumber);

        if (latestRecord.isPresent() && latestRecord.get().getTimeOut() == null) {
            // The car is exiting, update the 'out' time
            LicensePlate existingRecord = latestRecord.get();
            existingRecord.setTimeOut(LocalDateTime.now());
            repository.save(existingRecord);

            template.convertAndSend("/topic/refresh", "refresh");

        } else {
            // The car is entering, create a new record
            LicensePlate newEntry = new LicensePlate();
            newEntry.setPlateNumber(plateNumber);
            newEntry.setDriverName(driverName);
            newEntry.setTimeIn(LocalDateTime.now());
            // No need to set timeOut here, it will be null by default
            repository.save(newEntry);

        }

    }
}
