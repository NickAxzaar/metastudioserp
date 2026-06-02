package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String employeeCode;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private Double salary;
    private String phoneNumber;
    private Long userId;
    private String userName;
    private String userEmail;
}
