package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class LicensePlate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String plateNumber;
    private String driverName;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut; // new field for 'out' timestamp

    public LicensePlate() {

    }

    // Constructors, Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }

    public LicensePlate(Long id, String plateNumber, String driverName, LocalDateTime timeIn, LocalDateTime timeOut) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.driverName = driverName;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }
}