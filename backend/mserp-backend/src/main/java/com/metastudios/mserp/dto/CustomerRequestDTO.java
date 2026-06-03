package com.metastudios.mserp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    @NotBlank(message = "Customer name is required")
    @Size(min = 1, max = 100, message = "Customer name must be between 1 and 100 characters")
    private String customerName;

    @NotBlank(message = "Company name is required")
    @Size(min = 1, max = 100, message = "Company name must be between 1 and 100 characters")
    private String companyName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optionally starting with +")
    private String phoneNumber;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    @NotBlank(message = "Customer type is required")
    @Size(min = 1, max = 50, message = "Customer type must be between 1 and 50 characters")
    private String customerType;
}
