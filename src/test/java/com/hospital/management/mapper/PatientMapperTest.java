package com.hospital.management.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.hospital.management.dto.PatientDTO;
import com.hospital.management.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PatientMapperTest {

    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        patientMapper = new PatientMapper();
    }

    // @Test
    // void testToDTO_ValidPatient() {
    //     Patient patient = new Patient();
    //     patient.setId(1L);
    //     patient.setName("John Doe");
    //     patient.setAge(30);
    //     patient.setGender("Male");
    //     patient.setContactNumber("1234567890");

    //     PatientDTO dto = patientMapper.toDTO(patient);

    //     assertNotNull(dto);
    //     assertEquals(1L, dto.getId());
    //     assertEquals("John Doe", dto.getName());
    //     assertEquals(30, dto.getAge());
    //     assertEquals("Male", dto.getGender());
    //     assertEquals("1234567890", dto.getContactNumber());
    // }

    @Test
    void testToDTO_NullInput() {
        PatientDTO dto = patientMapper.toDTO(null);
        assertNull(dto);
    }

    // @Test
    // void testToEntity_ValidDTO() {
    //     PatientDTO dto = new PatientDTO();
    //     dto.setId(2L);
    //     dto.setName("Jane Doe");
    //     dto.setAge(25);
    //     dto.setGender("Female");
    //     dto.setContactNumber("9876543210");

    //     Patient patient = patientMapper.toEntity(dto);

    //     assertNotNull(patient);
    //     assertEquals(2L, patient.getId());
    //     assertEquals("Jane Doe", patient.getName());
    //     assertEquals(25, patient.getAge());
    //     assertEquals("Female", patient.getGender());
    //     assertEquals("9876543210", patient.getContactNumber());
    // }

    @Test
    void testToEntity_NullInput() {
        Patient patient = patientMapper.toEntity(null);
        assertNull(patient);
    }
}
