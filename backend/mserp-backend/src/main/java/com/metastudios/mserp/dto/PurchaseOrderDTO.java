package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PurchaseOrderDTO {
    private Long id;
    private Long vendorId;
    private String vendorName;
    private String vendorEmail;
    private String poCode;
    private LocalDate orderDate;
    private Double totalAmount;
    private String status;
    private String notes;
}
