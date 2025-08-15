package com.hospital.management.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.hospital.management.dto.DoctorDTO;
import com.hospital.management.entity.Doctor;
import com.hospital.management.repository.DoctorRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DoctorServiceImplTest {
    
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    private Doctor savedDoctor;

    @BeforeEach
    void setup(){
        doctorRepository.deleteAll();
        Doctor doctor = new Doctor();
        doctor.setName("XYZ");
        doctor.setSpecialization("Cardiology");

        savedDoctor = doctorRepository.save(doctor);
    }

    @Test
    public void testCreateDoctor(){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setName("John Doe");
        doctorDTO.setSpecialization("Cardiology");

        DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);

        assertNotNull(createdDoctor);
        assertEquals(doctorDTO.getName(), createdDoctor.getName());

        Doctor doctor = doctorRepository.findById(createdDoctor.getId()).orElse(null);
        assertNotNull(doctor);
        assertEquals(doctorDTO.getName(), doctor.getName());
    }

    @Test
    public void testGetDoctorById() {
        DoctorDTO doctorDTO = doctorService.getDoctorById(savedDoctor.getId());
        assertNotNull(doctorDTO);
        assertEquals(savedDoctor.getName(), doctorDTO.getName());
    }

    @Test
    public void testUpdateDoctor() {
        DoctorDTO doctorDTO = new DoctorDTO();

        doctorDTO.setName("John Doe");
        doctorDTO.setSpecialization("Cardiology");

        DoctorDTO savedDoctorDTO = doctorService.createDoctor(doctorDTO);

        savedDoctorDTO.setName("Bob");
        savedDoctorDTO.setSpecialization("General Physician");

        DoctorDTO updatedDoctor = doctorService.updateDoctor(savedDoctorDTO.getId(), savedDoctorDTO);
        assertNotNull(updatedDoctor);
        assertEquals(savedDoctorDTO.getName(), updatedDoctor.getName());


    }

    @Test
    public void testDeleteDoctor() {
        Long id = savedDoctor.getId();

        assertDoesNotThrow(() -> doctorService.deleteDoctor(id));

        assertFalse(doctorRepository.findById(id).isPresent());
    }

    @Test
    public void testGetAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        assertEquals(1, doctors.size());
    }

}
