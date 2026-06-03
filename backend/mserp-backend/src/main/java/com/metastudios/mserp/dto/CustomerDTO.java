package com.metastudios.mserp.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String customerName;
    private String companyName;
    private String email;
    private String phoneNumber;
    private String address;
    private String customerType;
    private Boolean isActive;
}
