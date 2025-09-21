package com.example.model;

public class Employee {
    private Integer employeeId;
    private String name;
    private String email;
    private String contactNumber;
    private String position;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(Integer employeeId, String name, String email, String contactNumber, String position) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.position = position;
    }

    // Getters and Setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
