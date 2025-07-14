package com.hospital.management.service;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.hospital.management.dto.AppointmentDTO;
import com.hospital.management.entity.Appointment;
import com.hospital.management.entity.AppointmentStatus;
import com.hospital.management.repository.AppointmentRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppointmentServiceImplTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    private Appointment savedAppointment;

    @BeforeEach
    void setup(){
        appointmentRepository.deleteAll();

        Appointment appointment = new Appointment();
        appointment.setDoctorId(101L);
        appointment.setPatientId(201L);
        appointment.setStatus(AppointmentStatus.BOOKED);

        savedAppointment = appointmentRepository.save(appointment);

    }
    
    @Test
    void testBookAppointment(){
        AppointmentDTO dto = new AppointmentDTO();
        dto.setDoctorId(101L);
        dto.setPatientId(202L);
        dto.setAppointmentDateTime(LocalDateTime.now());
        dto.setStatus("BOOKED");

        AppointmentDTO savedAppoinment = appointmentService.bookAppointment(dto);

        assertNotNull(savedAppoinment.getId());
        assertEquals(dto.getDoctorId(), savedAppoinment.getDoctorId());
        assertEquals(dto.getPatientId(), savedAppoinment.getPatientId());
        assertEquals(dto.getStatus(), savedAppoinment.getStatus());

        Appointment found = appointmentRepository.findById(savedAppoinment.getId()).orElse(null);
        assertNotNull(found);
    }

    @Test
    void testGetAppointmentById(){
        AppointmentDTO dto = appointmentService.getAppointmentById(savedAppointment.getId());

        assertNotNull(dto);
        assertEquals(101L, dto.getDoctorId());
    }

    @Test
    void testGetAppointmentById_NotFound(){
        Long invalidId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.getAppointmentById(invalidId);
        });

        assertEquals("Appointment not found with ID: " + invalidId,exception.getMessage());
    }

    @Test
    void testGetAppointmentsByDoctorId(){
        List<AppointmentDTO> dto = appointmentService.getAppointmentsByDoctorId(savedAppointment.getDoctorId());

        assertEquals(1, dto.size());

    }

    @Test
    void testGetAppointmentsByDoctorId_NotFound(){
        Long invalidDoctorId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.getAppointmentsByDoctorId(invalidDoctorId);
        });

        assertEquals("No appointment for Doctor with Id :" + invalidDoctorId, exception.getMessage());
    }

    @Test
    void testGetAppointmentsByPatientId(){
        List<AppointmentDTO> list = appointmentService.getAppointmentsByPatientId(savedAppointment.getPatientId());

        assertEquals(1, list.size());
    }

    @Test
    void testGetAppointmentsByPatientId_NotFound(){
        Long invalidPatientId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.getAppointmentsByPatientId(invalidPatientId);
        });

        assertEquals("No appointment for Patient with Id :" + invalidPatientId, exception.getMessage());
    }

    @Test
    void testCancelAppointment(){
        assertNotNull(savedAppointment);

        Long id = savedAppointment.getId();

        appointmentService.cancelAppointment(id);

        boolean exists = appointmentRepository.existsById(id);

        assertEquals(false, exists);
    }

    @Test
    void testCancelAppointment_NotFound(){
        Long invalidId = 999L;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.cancelAppointment(invalidId);
        });

        assertEquals("Appointment not found with ID: " + invalidId, exception.getMessage());
    }
}
