package com.hospital.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.management.dto.AppointmentDTO;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.AppointmentStatus;
import com.hospital.management.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Appointment savedAppointment;

    @BeforeEach
    void setup() {
        appointmentRepository.deleteAll();

        Appointment appointment = new Appointment();
        appointment.setDoctorId(101L);
        appointment.setPatientId(201L);
        appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        appointment.setStatus(AppointmentStatus.BOOKED);

        savedAppointment = appointmentRepository.save(appointment);
    }

    @Test
    void testBookAppointment() throws Exception {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setDoctorId(102L);
        dto.setPatientId(202L);
        dto.setAppointmentDateTime(LocalDateTime.now().plusDays(2));
        dto.setStatus("BOOKED");

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.doctorId", is(102)))
                .andExpect(jsonPath("$.status", is("BOOKED")));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        mockMvc.perform(get("/api/appointments/{id}", savedAppointment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorId", is(101)))
                .andExpect(jsonPath("$.patientId", is(201)));
    }

    @Test
    void testGetAppointmentById_NotFound() throws Exception {
        mockMvc.perform(get("/api/appointments/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAppointmentsByDoctor() throws Exception {
        mockMvc.perform(get("/api/appointments/doctor/{doctorId}", 101L))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "1"))
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void testGetAppointmentsByPatient() throws Exception {
        mockMvc.perform(get("/api/appointments/patient/{patientId}", 201L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void testCancelAppointment() throws Exception {
        mockMvc.perform(delete("/api/appointments/{id}", savedAppointment.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCancelAppointment_NotFound() throws Exception {
        mockMvc.perform(delete("/api/appointments/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
