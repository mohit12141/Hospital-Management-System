package com.hospital.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.hospital.management.dto.DoctorDTO;
import com.hospital.management.entity.Doctor;
import com.hospital.management.mapper.DoctorMapper;
import com.hospital.management.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    
    private DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        return DoctorMapper.toDTO(doctorRepository.save(DoctorMapper.toEntity(doctorDTO)));
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .map(DoctorMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No Doctor found with id " + id));
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor existing = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("No Doctor found with id " + id));
        existing.setName(doctorDTO.getName());
        existing.setSpecialization(doctorDTO.getSpecialization());
        return DoctorMapper.toDTO(doctorRepository.save(existing));
    }

    @Override
    public void deleteDoctor(Long id) {
        if(!doctorRepository.existsById(id)) {
            throw new RuntimeException("No Doctor found with id " + id);
        }
        doctorRepository.deleteById(id);
    }
}
