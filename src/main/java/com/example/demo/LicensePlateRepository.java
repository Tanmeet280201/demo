package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> {
    Optional<LicensePlate> findFirstByPlateNumberOrderByTimeInDesc(String plateNumber);
}