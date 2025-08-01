package com.hospital.management.service;

import com.hospital.management.dto.PatientDTO;
import com.hospital.management.entity.Patient;
// import com.hospital.management.mapper.PatientMapper;
import com.hospital.management.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
// import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientServiceImplTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    // @Autowired
    // private PatientMapper patientMapper;

    private Patient savedPatient;

    @BeforeEach
    void setUp() {
        patientRepository.deleteAll();

        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setAge(30);
        patient.setGender("Male");

        savedPatient = patientRepository.save(patient);
    }

    @Test
    void testGetAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();

        assertEquals(1, patients.size());
        assertEquals("John Doe", patients.get(0).getName());
    }

    @Test
    void testGetPatientById() {
        PatientDTO patientDTO = patientService.getPatientById(savedPatient.getId());

        assertNotNull(patientDTO);
        assertEquals("John Doe", patientDTO.getName());
        assertEquals(30, patientDTO.getAge());
    }
    
    @Test
    void testGetPatientById_IdNotFound(){
        Long invalidId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientById(invalidId);
        });
        
        assertEquals("Patient not found with Id " + invalidId, exception.getMessage());
    }

    @Test
    void testCreatePatient() {
        PatientDTO newPatient = new PatientDTO();
        newPatient.setName("Alice Smith");
        newPatient.setAge(25);
        newPatient.setGender("Female");

        PatientDTO savedDTO = patientService.createPatient(newPatient);

        assertNotNull(savedDTO.getId());
        assertEquals("Alice Smith", savedDTO.getName());

        // Verify from DB
        Patient found = patientRepository.findById(savedDTO.getId()).orElse(null);
        assertNotNull(found);
    }

    @Test
    void updatePatient() {
        PatientDTO newPatient = new PatientDTO();
        // newPatient.setId(1L);
        newPatient.setName("Alice Smith");
        newPatient.setAge(25);
        newPatient.setGender("Female");

        PatientDTO savedDTO = patientService.createPatient(newPatient);

        savedDTO.setName("Bob Marley");
        savedDTO.setAge(23);
        savedDTO.setGender("Male");

        PatientDTO updatedPatient = patientService.updatePatient(savedDTO.getId(), savedDTO);

        assertEquals(savedDTO.getName(), updatedPatient.getName());
        assertEquals(savedDTO.getAge(), updatedPatient.getAge());
        assertEquals(savedDTO.getGender(), updatedPatient.getGender());
    }
    
    @Test
    void testUpdatePatient_WhenPatientDoesNotExist(){
        Long invalidId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.updatePatient(invalidId, null);
        });

        assertEquals("Patient not found with Id " + invalidId, exception.getMessage());
    }

    @Test
    void testDeletePatient() {
        Long id = savedPatient.getId();

        assertDoesNotThrow(() -> patientService.deletePatient(id));

        assertFalse(patientRepository.findById(id).isPresent());
    }

    @Test
    void testDeletePatient_WhenPatientDoesNotExist_ShouldThrowException(){
        Long nonExistentId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.deletePatient(nonExistentId);
        });                                   

        assertEquals("Patient not found with Id " + nonExistentId, exception.getMessage());
    }


}
