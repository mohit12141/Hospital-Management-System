package com.hospital.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.PatientDTO;
import com.hospital.management.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    
    private PatientService patientService;
    
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    // create a new Patient
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO){
        PatientDTO created = patientService.createPatient(patientDTO);
        return ResponseEntity.ok(created);
    }

    // get all Patients
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients(){
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    } 

    // get patient by Id
    @GetMapping("/{Id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long Id){
        PatientDTO patient = patientService.getPatientById(Id);
        return ResponseEntity.ok(patient);
    }

    // delete a patient by id
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long Id){
        patientService.deletePatient(Id);
        return ResponseEntity.noContent().build();
    }

}
