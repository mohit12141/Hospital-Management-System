package com.hospital.management.mapper;

import com.hospital.management.dto.DoctorDTO;
import com.hospital.management.entity.Doctor;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return new DoctorDTO(doctor.getId(), doctor.getName(), doctor.getSpecialization());
    }

    public static Doctor toEntity(DoctorDTO doctorDTO) {
        if (doctorDTO == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        return doctor;
    }
}
