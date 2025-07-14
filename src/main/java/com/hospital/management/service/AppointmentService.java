package com.hospital.management.service;

import java.util.List;

import com.hospital.management.dto.AppointmentDTO;


public interface AppointmentService {
    AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO);

    AppointmentDTO getAppointmentById(Long id);

    List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId);

    List<AppointmentDTO> getAppointmentsByPatientId(Long patientId);

    void cancelAppointment(Long id);
}
