package com.metastudios.mserp.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDTO {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotBlank(message = "Employee code is required")
    @Size(min = 1, max = 50, message = "Employee code must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Z0-9_-]*$", message = "Employee code must contain only uppercase letters, numbers, hyphens, and underscores")
    private String employeeCode;

    @NotBlank(message = "Department is required")
    @Size(min = 1, max = 100, message = "Department must be between 1 and 100 characters")
    private String department;

    @NotBlank(message = "Designation is required")
    @Size(min = 1, max = 100, message = "Designation must be between 1 and 100 characters")
    private String designation;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be a positive number")
    private Double salary;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits, optionally starting with +")
    private String phoneNumber;
}
