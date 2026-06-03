package com.metastudios.mserp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VendorRequestDTO {

    @NotBlank(message = "Vendor name is required")
    @Size(min = 1, max = 100, message = "Vendor name must be between 1 and 100 characters")
    private String vendorName;

    @NotBlank(message = "Vendor code is required")
    @Size(min = 1, max = 50, message = "Vendor code must be between 1 and 50 characters")
    private String vendorCode;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optionally starting with +")
    private String phoneNumber;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Size(max = 100, message = "Payment terms must not exceed 100 characters")
    private String paymentTerms;
}
