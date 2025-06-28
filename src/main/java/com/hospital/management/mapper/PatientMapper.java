package com.hospital.management.mapper;

import org.springframework.stereotype.Component;

import com.hospital.management.dto.PatientDTO;
import com.hospital.management.entity.Patient;

@Component
public class PatientMapper {
    
    public PatientDTO toDTO(Patient patient){
        if(patient == null) return null;
        return new PatientDTO(
            patient.getId(),
            patient.getName(),
            patient.getAge(),
            patient.getGender(),
            patient.getContactNumber()
        );
    }

    public Patient toEntity(PatientDTO dto){
        if(dto == null) return null;
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setAge(dto.getAge());
        patient.setName(dto.getName());
        patient.setGender(dto.getGender());
        patient.setContactNumber(dto.getContactNumber());

        return patient;
    }
}
