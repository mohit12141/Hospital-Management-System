package com.hospital.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hospital.management.dto.AppointmentDTO;
import com.hospital.management.entity.Appointment;
import com.hospital.management.mapper.AppointmentMapper;
import com.hospital.management.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    } 

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = AppointmentMapper.toEntity(appointmentDTO);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(savedAppointment);
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        return AppointmentMapper.toDTO(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        List<Appointment> list = appointmentRepository.findByDoctorId(doctorId);
        if(list.isEmpty()){
            throw new RuntimeException("No appointment for Doctor with Id :" + doctorId);
        }
        return list
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> list = appointmentRepository.findByPatientId(patientId);
        if(list.isEmpty()){
            throw new RuntimeException("No appointment for Patient with Id :" + patientId);
        }
        return list
                .stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelAppointment(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }
    
}
