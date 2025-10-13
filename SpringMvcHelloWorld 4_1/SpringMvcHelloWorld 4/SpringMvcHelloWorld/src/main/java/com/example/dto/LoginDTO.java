package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Login
 * Used for API requests and responses
 */
public class LoginDTO {
    
    private Long loginId;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
    
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[+]?[0-9\\s\\-\\(\\)]{10,20}$", message = "Contact number format is invalid")
    private String contactNumber;
    
    @NotBlank(message = "Position is required")
    @Size(min = 2, max = 100, message = "Position must be between 2 and 100 characters")
    private String position;

    // Default constructor
    public LoginDTO() {
    }

    // Parameterized constructor
    public LoginDTO(Long loginId, String name, String email, String contactNumber, String position) {
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.position = position;
    }
    
    // Constructor without ID (for new logins)
    public LoginDTO(String name, String email, String contactNumber, String position) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.position = position;
    }

    // Getters and Setters
    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
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
        return "LoginDTO{" +
                "loginId=" + loginId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}








