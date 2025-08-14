package com.hospital.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.management.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    // Custom query methods (if needed) can be defined here
}