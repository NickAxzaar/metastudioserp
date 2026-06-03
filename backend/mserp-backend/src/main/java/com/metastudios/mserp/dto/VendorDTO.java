package com.metastudios.mserp.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private Long id;
    private String vendorName;
    private String vendorCode;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String paymentTerms;
    private Boolean isActive;
}
