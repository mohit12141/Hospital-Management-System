package com.hospital.management.mapper;

import com.hospital.management.dto.AppointmentDTO;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.AppointmentStatus;

public class AppointmentMapper {
    
    public static Appointment toEntity(AppointmentDTO dto){
        Appointment appointment = new Appointment();
        
        appointment.setId(dto.getId());
        appointment.setPatientId(dto.getPatientId());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus().toUpperCase()));

        return appointment;
    }

    public static AppointmentDTO toDTO(Appointment appointment){
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setDoctorId(appointment.getDoctorId());
        appointmentDTO.setPatientId(appointment.getPatientId());
        appointmentDTO.setAppointmentDateTime(appointment.getAppointmentDateTime());
        appointmentDTO.setStatus(appointment.getStatus().name());

        return appointmentDTO;
    }
}
