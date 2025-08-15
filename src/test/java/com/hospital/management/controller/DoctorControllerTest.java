package com.hospital.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.management.entity.Doctor;
import com.hospital.management.repository.DoctorRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository docRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Doctor savedDoctor;

    @BeforeEach
    void setup() {
        docRepository.deleteAll();
        
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Smith");
        doctor.setSpecialization("Cardiology");

        savedDoctor = docRepository.save(doctor);
    }

    // Add test methods for DoctorController here
    @Test
    void testGetDoctorById() throws Exception {
        mockMvc.perform(get("/api/doctors/{id}", savedDoctor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(savedDoctor.getName()))
                .andExpect(jsonPath("$.specialization").value(savedDoctor.getSpecialization()));
    }

    @Test
    void testGetAllDoctors() throws Exception {
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(savedDoctor.getName()))
                .andExpect(jsonPath("$[0].specialization").value(savedDoctor.getSpecialization()));
    }

    @Test
    void testCreateDoctor() throws Exception {
        Doctor newDoctor = new Doctor();
        newDoctor.setName("Dr. Jane Doe");
        newDoctor.setSpecialization("Neurology");

        mockMvc.perform(post("/api/doctors")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newDoctor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newDoctor.getName()))
                .andExpect(jsonPath("$.specialization").value(newDoctor.getSpecialization()));
    }

    @Test
    void testDeleteDoctor() throws Exception {
        mockMvc.perform(delete("/api/doctors/{id}", savedDoctor.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/doctors/{id}", savedDoctor.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateDoctor() throws Exception {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Dr. John Doe");
        updatedDoctor.setSpecialization("Pediatrics");

        mockMvc.perform(put("/api/doctors/{id}", savedDoctor.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedDoctor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedDoctor.getName()))
                .andExpect(jsonPath("$.specialization").value(updatedDoctor.getSpecialization()));
    }

    @Test
    void testGetDoctorById_NotFound() throws Exception {
        mockMvc.perform(get("/api/doctors/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No Doctor found with id 999")));
    }

    @Test
    void testDeleteDoctor_NotFound() throws Exception {
        mockMvc.perform(delete("/api/doctors/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No Doctor found with id 999")));
    }

    @Test
    void testUpdateDoctor_NotFound() throws Exception {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Dr. John Doe");
        updatedDoctor.setSpecialization("Pediatrics");

        mockMvc.perform(put("/api/doctors/{id}", 999L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedDoctor)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No Doctor found with id 999")));
    }

}
