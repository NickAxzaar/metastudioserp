package com.metastudios.mserp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LeadDTO {
    private Long id;
    private String leadName;
    private String companyName;
    private String email;
    private String phoneNumber;
    private String source;
    private String status;
    private String assignedTo;
    private LocalDate createdDate;
}
