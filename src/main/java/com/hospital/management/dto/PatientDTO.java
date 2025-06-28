package com.hospital.management.dto;

public class PatientDTO {
    private Long id;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;

    public PatientDTO() {
    
    }
    
    public PatientDTO(Long id, String name, int age, String gender, String contactNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
