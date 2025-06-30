package com.hospital.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.management.dto.PatientDTO;
import com.hospital.management.entity.Patient;
import com.hospital.management.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient savedPatient;

    @BeforeEach
    void setup() {
        patientRepository.deleteAll();

        Patient patient = new Patient();
        patient.setName("John Wick");
        patient.setAge(40);
        patient.setGender("Male");

        savedPatient = patientRepository.save(patient);
    }

    @Test
    void testGetAllPatients() throws Exception {
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Wick")));
    }

    @Test
    void testGetPatientById() throws Exception {
        mockMvc.perform(get("/api/patients/{id}", savedPatient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Wick")))
                .andExpect(jsonPath("$.age", is(40)));
    }

    @Test
    void testCreatePatient() throws Exception {
        PatientDTO newPatient = new PatientDTO();
        newPatient.setName("Alice Smith");
        newPatient.setAge(28);
        newPatient.setGender("Female");

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("Alice Smith")));
    }

    @Test
    void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/api/patients/{id}", savedPatient.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetPatientById_NotFound() throws Exception {
        mockMvc.perform(get("/api/patients/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Patient not found with Id 999")));
    }

    @Test
    void testDeletePatient_NotFound() throws Exception {
        mockMvc.perform(delete("/api/patients/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Patient not found with Id 999")));
    }
}
