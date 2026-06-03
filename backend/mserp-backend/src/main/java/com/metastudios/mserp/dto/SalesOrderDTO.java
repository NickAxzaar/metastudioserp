package com.metastudios.mserp.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class SalesOrderDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private String orderCode;
    private LocalDate orderDate;
    private Double totalAmount;
    private String status;
    private String notes;
}
