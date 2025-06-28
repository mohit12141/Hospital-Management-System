package com.hospital.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.management.dto.PatientDTO;
import com.hospital.management.entity.Patient;
import com.hospital.management.mapper.PatientMapper;
import com.hospital.management.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        return patientMapper.toDTO(patientRepository.save(patient));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
               .stream()
               .map(patientMapper::toDTO)
               .collect(Collectors.toList());
    }

    @Override
    public PatientDTO getPatientById(Long Id) {
        Patient patient = patientRepository.findById(Id)
                            .orElseThrow(() -> new RuntimeException("Patient not found with Id " + Id));
        return patientMapper.toDTO(patient);
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient existing = patientRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Patient not found with Id " + id));
        existing.setAge(patientDTO.getAge());
        existing.setName(patientDTO.getName());
        existing.setGender(patientDTO.getGender());
        existing.setContactNumber(patientDTO.getContactNumber());
        return patientMapper.toDTO(patientRepository.save(existing));
    }

    @Override
    public void deletePatient(Long id) {
        if(!patientRepository.existsById(id)){
            throw new RuntimeException("Patient not found with Id " + id);
        }
        patientRepository.deleteById(id);
    }
    
}
