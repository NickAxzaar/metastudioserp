package com.metastudios.mserp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LeadRequestDTO {

    @NotBlank(message = "Lead name is required")
    @Size(min = 1, max = 100, message = "Lead name must be between 1 and 100 characters")
    private String leadName;

    @NotBlank(message = "Company name is required")
    @Size(min = 1, max = 100, message = "Company name must be between 1 and 100 characters")
    private String companyName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optionally starting with +")
    private String phoneNumber;

    @NotBlank(message = "Source is required")
    @Size(min = 1, max = 50, message = "Source must be between 1 and 50 characters")
    private String source;

    @NotBlank(message = "Status is required")
    private String status;

    @Size(max = 100, message = "Assigned to must not exceed 100 characters")
    private String assignedTo;
}
